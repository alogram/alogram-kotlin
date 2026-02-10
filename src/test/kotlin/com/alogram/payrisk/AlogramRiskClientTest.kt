package com.alogram.payrisk

import com.alogram.payrisk.exceptions.AuthenticationException

import com.alogram.payrisk.exceptions.ScopedAccessError

import com.alogram.payrisk.v1.models.*

import kotlinx.coroutines.runBlocking

import okhttp3.mockwebserver.MockResponse

import okhttp3.mockwebserver.MockWebServer

import org.junit.jupiter.api.AfterEach

import org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertThrows



// Custom implementation of PaymentMethod interface for testing

data class PaymentMethodTestImpl(

    override val type: PaymentMethod.Type,

    override val cardNetwork: CardNetworkEnum? = null,

    override val cardType: PaymentCardTypeEnum? = null,

    override val bin: String? = null,

    override val issuerCountry: String? = null,

    override val avsResult: AvsResultEnum? = null,

    override val cvvResult: CvvResultEnum? = null,

    override val scaMethod: ScaMethodEnum? = null,

    override val threeDS: ThreeDSData? = null,

    override val walletType: PaymentWalletTypeEnum? = null,

    override val realtimeType: PaymentRealtimeTypeEnum? = null

) : PaymentMethod



class AlogramRiskClientTest {

    private val server = MockWebServer()



    @AfterEach

    fun teardown() {

        server.shutdown()

    }



    @Test

    fun `dual trust initialization`() {

        // Risk client blocks pk_

        assertThrows<ScopedAccessError> {

            AlogramRiskClient.Builder().apiKey("pk_test").build()

        }



        // Public client blocks sk_

        assertThrows<ScopedAccessError> {

            AlogramPublicClient.Builder().apiKey("sk_test").build()

        }

    }



    @Test

    fun `checkRisk success`() = runBlocking {

        server.enqueue(MockResponse()

            .setResponseCode(200)

            .setHeader("Content-Type", "application/json")

            .setBody("""

                {

                    "assessmentId": "as_12345678901234567890123456789012",

                    "decision": "approve", 

                    "riskScore": 0.1, 

                    "decisionAt": "2025-01-01T00:00:00Z"

                }

            """.trimIndent()))



        val client = AlogramRiskClient.Builder()

            .baseUrl(server.url("/").toString())

            .apiKey("sk_test")

            .build()



        val request = CheckRequest(

            entities = EntityIds(tenantId = "tid_123"),

            purchase = Purchase(

                amount = 99.00f,

                currency = "USD",

                paymentMethod = PaymentMethodTestImpl(type = PaymentMethod.Type.CARD, bin = "424242")

            )

        )



        val response = client.checkRisk(request)

        assertEquals("approve", response.decision.value)

        

        val recordedRequest = server.takeRequest()

        assertEquals("sk_test", recordedRequest.getHeader("x-api-key"))

    }



    @Test

    fun `checkRisk retry logic`() = runBlocking {

        server.enqueue(MockResponse().setResponseCode(500))

        server.enqueue(MockResponse().setResponseCode(500))

        server.enqueue(MockResponse()

            .setResponseCode(200)

            .setHeader("Content-Type", "application/json")

            .setBody("""

                {

                    "assessmentId": "as_12345678901234567890123456789012",

                    "decision": "approve", 

                    "riskScore": 0.1, 

                    "decisionAt": "2025-01-01T00:00:00Z"

                }

            """.trimIndent()))



        val client = AlogramRiskClient.Builder()

            .baseUrl(server.url("/").toString())

            .apiKey("sk_test")

            .build()



        val request = CheckRequest(

            entities = EntityIds(tenantId = "tid_123"),

            purchase = Purchase(

                amount = 99.00f,

                currency = "USD",

                paymentMethod = PaymentMethodTestImpl(type = PaymentMethod.Type.CARD, bin = "424242")

            )

        )



        val response = client.checkRisk(request)

        assertEquals("approve", response.decision.value)

        assertEquals(3, server.requestCount)

    }

}
