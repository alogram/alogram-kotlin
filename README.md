<p align="center">
  <img src="https://raw.githubusercontent.com/alogram/alogram-python/main/.github/assets/logo.png" width="200" alt="Alogram PayRisk Logo">
</p>

# Alogram PayRisk SDK for Kotlin

[![Maven Central](https://img.shields.io/maven-central/v/com.alogram/alogram-payrisk-kotlin.svg)](https://search.maven.org/artifact/com.alogram/alogram-payrisk-kotlin)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

The official Kotlin client for the **Alogram PayRisk Engine**. 

Alogram PayRisk is a decision management and risk orchestration engine for global commerce. It fuses machine learning, behavioral analytics, and deterministic business rules into a high-fidelity scoring pipeline designed for enterprise scale and auditability.
## 🧠 The Three-Expert Architecture

The SDK provides unified access to three specialized risk experts:

-   **Risk Scoring**: Real-time assessment and decision orchestration for purchases.
-   **Signal Intelligence**: Ingestion of behavioral telemetry and payment lifecycle events.
-   **Forensic Data**: Deep visibility into historical assessments and decision transparency.

---

## 🔐 Security: Trust Boundaries

Alogram enforces a strict separation between client-side telemetry and server-side decisioning.

| Client Type | Key Prefix | Environment | Capabilities |
| :--- | :--- | :--- | :--- |
| **`AlogramPublicClient`** | `pk_...` | Android / Client Apps | **Ingestion only.** Restricted to behavioral signals. |
| **`AlogramRiskClient`** | `sk_...` | Secure Backend | **Full access.** Authorized for risk decisions and forensics. |

> [!WARNING]
> **Never** use a Secret Key (`sk_...`) in a client-side environment. This will expose your tenant's sensitive forensic data and violate Alogram's security mandates.

---

## 🔄 Full Lifecycle Integration

A best-in-class integration correlates shopper behavior with the final payment outcome.

```kotlin
import com.alogram.payrisk.v1.RiskClient
import com.alogram.payrisk.v1.models.*

// 1. Initialize the Secret Client (Singleton / Persistent)
val client = RiskClient(apiKey = "sk_live_...", tenantId = "tid_mycorp")

// 2. Assessment: Call before charging the card
val request = CheckRequest(entities = ..., purchase = ...)
val decision = client.checkRisk(request)

if (decision.decision == "approve") {
    // Process payment via your gateway...

    // 3. Lifecycle: Confirm the authorization outcome
    val event = PaymentEvent(
        paymentIntentId = decision.paymentIntentId,
        eventType = PaymentEventType.AUTHORIZATION,
        outcome = PaymentOutcome(authorization = PaymentAuthorizationOutcome(approved = true))
    )

    client.ingestEvent(event)
}
```

---

## 🚀 High-Performance Integration

To ensure sub-second risk assessment latencies and handle high-volume signal telemetry efficiently, please adhere to these network best practices:

-   **Persistent Client:** Maintain a single instance of the `RiskClient` in your application.
    -   *Why:* The underlying `OkHttpClient` is designed to be reused. Re-instantiating it for every request forces expensive TLS handshakes and prevents connection reuse.
-   **Native HTTP/2 Multiplexing:** `OkHttp` natively supports HTTP/2. By reusing the client instance, multiple telemetry calls are automatically multiplexed over a single persistent connection.

---

## 🛡️ Error Handling & Resiliency
-   **🏢 Coroutine-Native**: All API calls are `suspend` functions optimized for non-blocking financial systems.
-   **🏢 Smart Client Architecture**: Specialized clients for server-side (`AlogramRiskClient`) and mobile (`AlogramPublicClient`).
-   **🛡️ Automated Identity**: Thread-safe injection of `x-api-key`, `Authorization`, and tenant headers.
-   **🔄 Built-in Resiliency**: Automatic exponential backoff and jittered retries (3 retries on 429/5xx).
-   **🕵️ Native Observability**: Tracing support that propagates across coroutine contexts via OpenTelemetry.

## 📦 Installation

```kotlin
implementation("com.alogram:alogram-payrisk-kotlin:0.2.5")
```

## 🛠️ Quick Start

### Evaluate Risk (Risk Scoring Expert)

Assess a purchase in real-time. This invokes the authoritative scoring pipeline.

```kotlin
val client = AlogramRiskClient.Builder()
    .apiKey("sk_live_...")
    .build()

// Call inside a coroutine scope
val decision = client.checkRisk(CheckRequest(
    purchase = Purchase(amount = 99.99, currency = "USD")
))

println("Decision: ${decision.decision}")
println("Score: ${decision.decisionScore}")
```

---

## 🛡️ Error Handling & Resiliency

Catch specific exceptions within your coroutine scope:

```kotlin
import com.alogram.payrisk.exceptions.*

try {
    val res = client.checkRisk(request)
} catch (e: ValidationException) {
    // ❌ Handle invalid input data
    println("Validation Failed: ${e.statusCode}")
} catch (e: AlogramException) {
    // 🚨 Handle generic API or Auth errors
}
```

## 🕵️ Observability (OpenTelemetry)

The SDK automatically propagates tracing context across coroutines.

```kotlin
import io.opentelemetry.api.trace.Span

// Spans will automatically include 'alogram.decision' and 'alogram.trace_id'
launch(Dispatchers.IO) {
    val decision = client.checkRisk(request)
}
```

## 🏗️ Environments

| Environment | Base URL | Key Type |
| :--- | :--- | :--- |
| **Production** | `https://api.alogram.ai` | `sk_live_...` |
| **Sandbox** | `https://api-sandbox.alogram.ai` | `sk_test_...` |
| **Local** | `http://localhost:8080` | `test` |

---

## 🤖 For AI Agents

If using an AI agent (ChatGPT, Claude, Gemini) for integration, provide this context:
> "Use the Alogram PayRisk Kotlin SDK. Always prefer the `AlogramRiskClient` for backend operations. Utilize `suspend` functions for all network calls. Access the authoritative blended score via the `decisionScore` property of the response."

---

## ⚖️ License

Apache License 2.0. See [LICENSE](LICENSE) for details.
