package com.alogram.payrisk.examples

import com.alogram.payrisk.AlogramRiskClient
import com.alogram.payrisk.v1.models.*
import kotlinx.coroutines.runBlocking

// Custom implementation of PaymentMethod interface for Kotlin
data class PaymentMethodImpl(
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

fun main() = runBlocking {
    val client = AlogramRiskClient.Builder()
        .apiKey("sk_test_123")
        .build()

    val request = CheckRequest(
        entities = EntityIds(tenantId = "tid_test"),
        purchase = Purchase(
            amount = 150.00f,
            currency = "USD",
            paymentMethod = PaymentMethodImpl(
                type = PaymentMethod.Type.CARD,
                bin = "424242",
                cardNetwork = CardNetworkEnum.VISA
            )
        )
    )

    try {
        val response = client.checkRisk(request)
        println("Risk Decision: ${response.decision} (Score: ${response.riskScore})")
    } catch (e: Exception) {
        System.err.println("Failed to check risk: ${e.message}")
    }
}
