package com.alogram.payrisk

import com.alogram.payrisk.exceptions.*
import com.alogram.payrisk.v1.api.PayriskApiClient
import com.alogram.payrisk.v1.models.*
import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.api.trace.Tracer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.openapitools.client.infrastructure.ApiClient
import retrofit2.Response
import retrofit2.awaitResponse
import java.util.UUID
import java.util.logging.Logger
import okhttp3.Interceptor

/**
 * 🔒 Internal base class for shared Kotlin SDK logic.
 */
abstract class AlogramBaseClient(
    protected val apiClient: ApiClient,
    protected val api: PayriskApiClient
) {
    protected val tracer: Tracer = GlobalOpenTelemetry.getTracer("alogram.payrisk")

    protected fun generateId(): String = UUID.randomUUID().toString()

    protected fun isRetryable(code: Int): Boolean = code == 429 || code >= 500

    protected fun mapResponseToException(response: Response<*>): AlogramException {
        val code = response.code()
        val body = response.errorBody()?.string()
        val msg = "API Error $code"

        return when (code) {
            401, 403 -> AuthenticationException(msg, code, body)
            429 -> RateLimitException(msg, code, body)
            400, 422 -> ValidationException(msg, code, body)
            else -> if (code >= 500) InternalServerException(msg, code, body) else AlogramException(msg, code, body)
        }
    }
}

/**
 * Configuration options for the Alogram Kotlin SDK.
 */
data class AlogramClientOptions(
    val baseUrl: String = "https://api.alogram.ai",
    val apiKey: String? = null,
    val accessToken: String? = null,
    val tenantId: String? = null,
    val clientId: String? = null,
    val debug: Boolean = false
)

/**
 * 🏢 **AlogramRiskClient** (Secret Client)
 * Designed for server-side environments using a Secret Key (`sk_...`).
 */
class AlogramRiskClient private constructor(apiClient: ApiClient, api: PayriskApiClient) : AlogramBaseClient(apiClient, api) {

    class Builder {
        private var options = AlogramClientOptions()

        fun baseUrl(url: String) = apply { options = options.copy(baseUrl = url) }
        fun apiKey(key: String) = apply { options = options.copy(apiKey = key) }
        fun accessToken(token: String) = apply { options = options.copy(accessToken = token) }
        fun tenantId(id: String) = apply { options = options.copy(tenantId = id) }
        fun clientId(id: String) = apply { options = options.copy(clientId = id) }
        fun debug(enabled: Boolean) = apply { options = options.copy(debug = enabled) }

        fun build(): AlogramRiskClient {
            if (options.apiKey?.startsWith("pk_") == true) {
                throw ScopedAccessError("Cannot initialize AlogramRiskClient with a Publishable Key (pk_...). Please use AlogramPublicClient.")
            }
            
            val headerInterceptor = Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                options.apiKey?.let { requestBuilder.header("x-api-key", it) }
                options.accessToken?.let { requestBuilder.header("Authorization", "Bearer $it") }
                options.tenantId?.let { requestBuilder.header("x-trusted-tenant-id", it) }
                options.clientId?.let { requestBuilder.header("x-trusted-client-id", it) }
                chain.proceed(requestBuilder.build())
            }

            val apiClient = ApiClient(options.baseUrl)
            apiClient.addAuthorization("DefaultHeaders", headerInterceptor)
            val api = apiClient.createService(PayriskApiClient::class.java)
            
            return AlogramRiskClient(apiClient, api)
        }
    }

    suspend fun checkRisk(request: CheckRequest, idempotencyKey: String? = null, traceId: String? = null): DecisionResponse {
        val ik = idempotencyKey ?: generateId()
        val tid = traceId ?: generateId()

        val span = tracer.spanBuilder("alogram.check_risk")
            .setAttribute("alogram.idempotency_key", ik)
            .setAttribute("alogram.trace_id", tid)
            .startSpan()

        try {
            var lastException: Exception? = null
            for (attempt in 1..3) {
                try {
                    val call: retrofit2.Call<DecisionResponse> = api.riskCheck(ik, request, tid)
                    val response: retrofit2.Response<DecisionResponse> = call.awaitResponse()
                    
                    if (response.isSuccessful) {
                        span.setStatus(StatusCode.OK)
                        val body = response.body() ?: throw AlogramException("Empty response body", response.code())
                        span.setAttribute("alogram.decision", body.decision.value)
                        return body
                    } else {
                        val exception = mapResponseToException(response)
                        if (!isRetryable(response.code()) || attempt == 3) throw exception
                        lastException = exception
                    }
                } catch (e: Exception) {
                    if (e is AlogramException && !isRetryable(e.statusCode)) throw e
                    if (attempt == 3) throw e
                    lastException = e
                }
                val baseDelay = Math.pow(2.0, (attempt - 1).toDouble()).toLong() * 1000
                val jitter = (Math.random() * (baseDelay * 0.2)).toLong()
                delay(baseDelay + jitter)
            }
            throw lastException!!
        } catch (e: Exception) {
            span.recordException(e)
            span.setStatus(StatusCode.ERROR, e.message ?: "Unknown error")
            throw e
        } finally {
            span.end()
        }
    }

    suspend fun ingestSignals(request: SignalsRequest, idempotencyKey: String? = null, traceId: String? = null) {
        val ik = idempotencyKey ?: generateId()
        val tid = traceId ?: generateId()

        val span = tracer.spanBuilder("alogram.ingest_signals")
            .setAttribute("alogram.idempotency_key", ik)
            .setAttribute("alogram.trace_id", tid)
            .startSpan()

        try {
            var lastException: Exception? = null
            for (attempt in 1..3) {
                try {
                    val response = api.ingestSignals(ik, request, tid).awaitResponse()
                    if (response.isSuccessful) {
                        span.setStatus(StatusCode.OK)
                        return
                    } else {
                        val exception = mapResponseToException(response)
                        if (!isRetryable(response.code()) || attempt == 3) throw exception
                        lastException = exception
                    }
                } catch (e: Exception) {
                    if (e is AlogramException && !isRetryable(e.statusCode)) throw e
                    if (attempt == 3) throw e
                    lastException = e
                }
                val baseDelay = Math.pow(2.0, (attempt - 1).toDouble()).toLong() * 1000
                val jitter = (Math.random() * (baseDelay * 0.2)).toLong()
                delay(baseDelay + jitter)
            }
            throw lastException!!
        } catch (e: Exception) {
            span.recordException(e)
            span.setStatus(StatusCode.ERROR, e.message ?: "Unknown error")
            throw e
        } finally {
            span.end()
        }
    }

    suspend fun ingestEvent(event: PaymentEvent, idempotencyKey: String? = null, traceId: String? = null) {
        val ik = idempotencyKey ?: generateId()
        val tid = traceId ?: generateId()

        val span = tracer.spanBuilder("alogram.ingest_event")
            .setAttribute("alogram.idempotency_key", ik)
            .setAttribute("alogram.trace_id", tid)
            .startSpan()

        try {
            var lastException: Exception? = null
            for (attempt in 1..3) {
                try {
                    val response = api.ingestPaymentEvent(ik, event, tid).awaitResponse()
                    if (response.isSuccessful) {
                        span.setStatus(StatusCode.OK)
                        return
                    } else {
                        val exception = mapResponseToException(response)
                        if (!isRetryable(response.code()) || attempt == 3) throw exception
                        lastException = exception
                    }
                } catch (e: Exception) {
                    if (e is AlogramException && !isRetryable(e.statusCode)) throw e
                    if (attempt == 3) throw e
                    lastException = e
                }
                val baseDelay = Math.pow(2.0, (attempt - 1).toDouble()).toLong() * 1000
                val jitter = (Math.random() * (baseDelay * 0.2)).toLong()
                delay(baseDelay + jitter)
            }
            throw lastException!!
        } catch (e: Exception) {
            span.recordException(e)
            span.setStatus(StatusCode.ERROR, e.message ?: "Unknown error")
            throw e
        } finally {
            span.end()
        }
    }
}

/**
 * 🌐 **AlogramPublicClient** (Public Client)
 * Designed for client-side environments (Browsers, Mobile) using a Publishable Key (`pk_...`).
 */
class AlogramPublicClient private constructor(apiClient: ApiClient, api: PayriskApiClient) : AlogramBaseClient(apiClient, api) {

    class Builder {
        private var options = AlogramClientOptions()

        fun baseUrl(url: String) = apply { options = options.copy(baseUrl = url) }
        fun apiKey(key: String) = apply { options = options.copy(apiKey = key) }
        fun accessToken(token: String) = apply { options = options.copy(accessToken = token) }
        fun tenantId(id: String) = apply { options = options.copy(tenantId = id) }
        fun clientId(id: String) = apply { options = options.copy(clientId = id) }
        fun debug(enabled: Boolean) = apply { options = options.copy(debug = enabled) }

        fun build(): AlogramPublicClient {
            if (options.apiKey?.startsWith("sk_") == true) {
                throw ScopedAccessError("Cannot initialize AlogramPublicClient with a Secret Key (sk_...). Please use AlogramRiskClient for server-side operations.")
            }
            
            val headerInterceptor = Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                options.apiKey?.let { requestBuilder.header("x-api-key", it) }
                options.accessToken?.let { requestBuilder.header("Authorization", "Bearer $it") }
                options.tenantId?.let { requestBuilder.header("x-trusted-tenant-id", it) }
                options.clientId?.let { requestBuilder.header("x-trusted-client-id", it) }
                chain.proceed(requestBuilder.build())
            }

            val apiClient = ApiClient(options.baseUrl)
            apiClient.addAuthorization("DefaultHeaders", headerInterceptor)
            val api = apiClient.createService(PayriskApiClient::class.java)
            
            return AlogramPublicClient(apiClient, api)
        }
    }

    suspend fun ingestSignals(request: SignalsRequest, idempotencyKey: String? = null, traceId: String? = null) {
        val ik = idempotencyKey ?: generateId()
        val tid = traceId ?: generateId()

        val span = tracer.spanBuilder("alogram.ingest_signals")
            .setAttribute("alogram.idempotency_key", ik)
            .setAttribute("alogram.trace_id", tid)
            .startSpan()

        try {
            var lastException: Exception? = null
            for (attempt in 1..3) {
                try {
                    val response = api.ingestSignals(ik, request, tid).awaitResponse()
                    if (response.isSuccessful) {
                        span.setStatus(StatusCode.OK)
                        return
                    } else {
                        val exception = mapResponseToException(response)
                        if (!isRetryable(response.code()) || attempt == 3) throw exception
                        lastException = exception
                    }
                } catch (e: Exception) {
                    if (e is AlogramException && !isRetryable(e.statusCode)) throw e
                    if (attempt == 3) throw e
                    lastException = e
                }
                val baseDelay = Math.pow(2.0, (attempt - 1).toDouble()).toLong() * 1000
                val jitter = (Math.random() * (baseDelay * 0.2)).toLong()
                delay(baseDelay + jitter)
            }
            throw lastException!!
        } catch (e: Exception) {
            span.recordException(e)
            span.setStatus(StatusCode.ERROR, e.message ?: "Unknown error")
            throw e
        } finally {
            span.end()
        }
    }
}
