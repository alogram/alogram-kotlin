// Copyright (c) 2025 Alogram Inc.
// All rights reserved.

package com.alogram.payrisk.testing

import com.alogram.payrisk.v1.models.*
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import kotlinx.coroutines.delay

/**
 * 🛠️ **MockRiskClient**
 * 
 * A zero-dependency mock implementation of the Alogram Risk Client for local testing.
 * Optimized for Kotlin Coroutines.
 */
class MockRiskClient {
    private val calls = Collections.synchronizedList(mutableListOf<Map<String, Any?>>())
    private val queuedResponses = ConcurrentLinkedQueue<Any>()
    private var defaultDecision = "approve"
    private var defaultScore = 0.1
    private var delayMs = 0L

    fun setDefaultDecision(decision: String, score: Double = 0.1) {
        this.defaultDecision = decision.lowercase()
        this.defaultScore = score
    }

    private fun getTimestamp(): String {
        return OffsetDateTime.now().format(DateTimeFormatter.ISO_INSTANT)
    }

    fun queueDecision(decision: String, score: Double = 0.1, reason: String? = "mock_reason") {
        val resp = DecisionResponse(
            assessmentId = "mock-${UUID.randomUUID().toString().take(12)}",
            decision = DecisionResponse.Decision.entries.find { it.value == decision.lowercase() } ?: DecisionResponse.Decision.APPROVE,
            decisionAt = getTimestamp(),
            riskScore = score.toFloat(),
            fraudScore = FraudScore(
                riskLevel = RiskLevelEnum.LOW,
                score = score.toFloat(),
                explanation = "Mocked response"
            ),
            reasons = if (reason != null) {
                listOf(ReasonDetail(
                    code = "MOCK_CODE",
                    category = RiskCategoryEnum.BEHAVIOR,
                    displayName = "Mock Reason",
                    description = reason
                ))
            } else emptyList()
        )
        queuedResponses.add(resp)
    }

    fun queueError(exception: Throwable) {
        queuedResponses.add(exception)
    }

    fun setDelay(ms: Long) {
        this.delayMs = ms
    }

    fun getCallCount() = calls.size

    fun getCalls(): List<Map<String, Any?>> = calls.toList()

    private suspend fun handleCall(method: String, request: Any?) {
        calls.add(mapOf("method" to method, "request" to request, "timestamp" to System.currentTimeMillis()))
        if (delayMs > 0) {
            delay(delayMs)
        }
    }

    suspend fun checkRisk(request: CheckRequest): DecisionResponse {
        handleCall("checkRisk", request)
        
        val item = queuedResponses.poll()
        if (item is Throwable) throw item
        if (item is DecisionResponse) return item

        return DecisionResponse(
            assessmentId = "mock-${UUID.randomUUID().toString().take(12)}",
            decision = DecisionResponse.Decision.entries.find { it.value == defaultDecision } ?: DecisionResponse.Decision.APPROVE,
            decisionAt = getTimestamp(),
            riskScore = defaultScore.toFloat(),
            fraudScore = FraudScore(
                riskLevel = RiskLevelEnum.LOW,
                score = defaultScore.toFloat()
            ),
            reasons = listOf(ReasonDetail(
                code = "DEFAULT",
                category = RiskCategoryEnum.BEHAVIOR,
                displayName = "Default",
                description = "default_mock_response"
            ))
        )
    }

    suspend fun ingestSignals(request: SignalsRequest) {
        handleCall("ingestSignals", request)
        val item = queuedResponses.poll()
        if (item is Throwable) throw item
    }

    suspend fun ingestEvent(event: PaymentEvent) {
        handleCall("ingestEvent", event)
        val item = queuedResponses.poll()
        if (item is Throwable) throw item
    }
}