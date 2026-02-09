package com.alogram.payrisk.v1.api

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

import com.alogram.payrisk.v1.models.AccountCheckRequest
import com.alogram.payrisk.v1.models.CheckRequest
import com.alogram.payrisk.v1.models.DecisionResponse
import com.alogram.payrisk.v1.models.KycCheckRequest
import com.alogram.payrisk.v1.models.PaymentEvent
import com.alogram.payrisk.v1.models.Problem
import com.alogram.payrisk.v1.models.ScoresSuccessResponse
import com.alogram.payrisk.v1.models.SignalsRequest

interface PayriskApiClient {
    /**
     * POST v1/risk/account/check
     * Synchronous fraud decision for account/session events (signup, login, settings)
     * 
     * Responses:
     *  - 200: Synchronous risk decision.
     *  - 400: An error response.
     *  - 422: An error response.
     *  - 429: An error response.
     *  - 500: An error response.
     *
     * @param xIdempotencyKey Unique Idempotency-Key sent in the POST request etc.
     * @param accountCheckRequest 
     * @param xTraceId Echoed or generated trace ID for tracking requests. (optional)
     * @return [Call]<[DecisionResponse]>
     */
    @POST("v1/risk/account/check")
    fun accountRiskCheck(@Header("x-idempotency-key") xIdempotencyKey: kotlin.String, @Body accountCheckRequest: AccountCheckRequest, @Header("x-trace-id") xTraceId: kotlin.String? = null): Call<DecisionResponse>

    /**
     * GET v1/scores/{tenantId}
     * Retrieve fraud scores for a customer
     * 
     * Responses:
     *  - 200: List of fraud scores for a customer.
     *  - 400: An error response.
     *  - 500: An error response.
     *
     * @param tenantId 
     * @param xTraceId Echoed or generated trace ID for tracking requests. (optional)
     * @param xIdempotencyKey Unique Idempotency-Key sent in the GET request etc. (optional)
     * @param startTime  (optional)
     * @param endTime  (optional)
     * @param pageSize  (optional, default to 50)
     * @param pageToken  (optional)
     * @return [Call]<[ScoresSuccessResponse]>
     */
    @GET("v1/scores/{tenantId}")
    fun getFraudScores(@Path("tenantId") tenantId: kotlin.String, @Header("x-trace-id") xTraceId: kotlin.String? = null, @Header("x-idempotency-key") xIdempotencyKey: kotlin.String? = null, @Query("startTime") startTime: kotlin.String? = null, @Query("endTime") endTime: kotlin.String? = null, @Query("pageSize") pageSize: kotlin.Int? = 50, @Query("pageToken") pageToken: kotlin.String? = null): Call<ScoresSuccessResponse>

    /**
     * GET v1/health
     * Health check for the service
     * 
     * Responses:
     *  - 200: Service is healthy
     *  - 503: Service is unavailable
     *
     * @return [Call]<[Unit]>
     */
    @GET("v1/health")
    fun healthCheck(): Call<Unit>

    /**
     * POST v1/events
     * Ingest payment lifecycle events (authorization, capture, settlement, refund, dispute, chargeback, chargeback_outcome). 
     * 
     * Responses:
     *  - 202: Accepted
     *  - 400: An error response.
     *  - 401: An error response.
     *  - 403: An error response.
     *  - 404: An error response.
     *  - 409: An error response.
     *  - 413: An error response.
     *  - 422: An error response.
     *  - 429: An error response.
     *  - 500: An error response.
     *
     * @param xIdempotencyKey Unique Idempotency-Key sent in the POST request etc.
     * @param paymentEvent 
     * @param xTraceId Echoed or generated trace ID for tracking requests. (optional)
     * @return [Call]<[Unit]>
     */
    @POST("v1/events")
    fun ingestPaymentEvent(@Header("x-idempotency-key") xIdempotencyKey: kotlin.String, @Body paymentEvent: PaymentEvent, @Header("x-trace-id") xTraceId: kotlin.String? = null): Call<Unit>

    /**
     * POST v1/signals
     * Ingest non-payment signals (account or interaction) for modeling
     * 
     * Responses:
     *  - 202: Accepted
     *  - 400: An error response.
     *  - 413: An error response.
     *  - 422: An error response.
     *  - 429: An error response.
     *  - 500: An error response.
     *
     * @param xIdempotencyKey Unique Idempotency-Key sent in the POST request etc.
     * @param signalsRequest 
     * @param xTraceId Echoed or generated trace ID for tracking requests. (optional)
     * @return [Call]<[Unit]>
     */
    @POST("v1/signals")
    fun ingestSignals(@Header("x-idempotency-key") xIdempotencyKey: kotlin.String, @Body signalsRequest: SignalsRequest, @Header("x-trace-id") xTraceId: kotlin.String? = null): Call<Unit>

    /**
     * POST v1/risk/kyc/check
     * Synchronous decision for KYC/identity verification
     * 
     * Responses:
     *  - 200: Synchronous risk decision.
     *  - 400: An error response.
     *  - 422: An error response.
     *  - 429: An error response.
     *  - 500: An error response.
     *
     * @param xIdempotencyKey Unique Idempotency-Key sent in the POST request etc.
     * @param kycCheckRequest 
     * @param xTraceId Echoed or generated trace ID for tracking requests. (optional)
     * @return [Call]<[DecisionResponse]>
     */
    @POST("v1/risk/kyc/check")
    fun kycRiskCheck(@Header("x-idempotency-key") xIdempotencyKey: kotlin.String, @Body kycCheckRequest: KycCheckRequest, @Header("x-trace-id") xTraceId: kotlin.String? = null): Call<DecisionResponse>

    /**
     * POST v1/risk/check
     * Synchronous fraud decision for a purchase
     * 
     * Responses:
     *  - 200: Synchronous risk decision.
     *  - 400: An error response.
     *  - 401: An error response.
     *  - 403: An error response.
     *  - 422: An error response.
     *  - 429: An error response.
     *  - 500: An error response.
     *
     * @param xIdempotencyKey Unique Idempotency-Key sent in the POST request etc.
     * @param checkRequest 
     * @param xTraceId Echoed or generated trace ID for tracking requests. (optional)
     * @return [Call]<[DecisionResponse]>
     */
    @POST("v1/risk/check")
    fun riskCheck(@Header("x-idempotency-key") xIdempotencyKey: kotlin.String, @Body checkRequest: CheckRequest, @Header("x-trace-id") xTraceId: kotlin.String? = null): Call<DecisionResponse>

}
