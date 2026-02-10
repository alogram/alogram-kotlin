package com.alogram.payrisk.examples

import com.alogram.payrisk.AlogramRiskClient
import com.alogram.payrisk.v1.models.*

suspend fun main() {
    val client = AlogramRiskClient.Builder()
        .apiKey("sk_test_123")
        .build()

    val request = CheckRequest(
        entities = EntityIds(tenantId = "tid_test"),
        purchase = Purchase(
            amount = 150.00f,
            currency = "USD",
            paymentMethod = PaymentMethod(
                card = Card(
                    type = "card",
                    bin = "424242",
                    cardNetwork = CardNetworkEnum.VISA
                )
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
