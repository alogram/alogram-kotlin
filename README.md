# Alogram PayRisk SDK for Kotlin

[![Maven Central](https://img.shields.io/maven-central/v/com.alogram/alogram-payrisk-kotlin.svg)](https://search.maven.org/artifact/com.alogram/alogram-payrisk-kotlin)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

The official Alogram PayRisk 'Smart' SDK for Kotlin. Engineered for modern, asynchronous financial systems using **Kotlin Coroutines**, native resiliency, and deep observability.

## 🚀 Features

-   **🏢 Coroutine-Native Architecture**: All API calls are `suspend` functions optimized for high-concurrency environments.
-   **🏢 Smart Client Architecture**: Specialized clients for server-side (`AlogramRiskClient`) and mobile (`AlogramPublicClient`).
-   **🛡️ Automated Identity**: Thread-safe injection of `x-api-key`, `Authorization`, and tenant headers.
-   **🔄 Built-in Resiliency**: Transparent exponential backoff and jittered retries (3 retries on 429/5xx).
-   **🕵️ OpenTelemetry Native**: Tracing support that propagates across coroutine contexts.

## 📦 Installation

```kotlin
implementation("com.alogram:alogram-payrisk-kotlin:0.1.6-rc.8")
```

## 🛠️ Quick Start

### Evaluate Risk (Server-Side)

```kotlin
val client = AlogramRiskClient.Builder()
    .apiKey("sk_live_your_secret_key")
    .build()

// Call inside a coroutine
val decision = client.checkRisk(CheckRequest(
    purchase = Purchase(amount = 99.99, currency = "USD")
))

println("Decision: ${decision.decision}")
```

---

## 🛡️ Error Handling

Catch specific exceptions within your coroutine scope:

```kotlin
import com.alogram.payrisk.exceptions.*

try {
    val res = client.checkRisk(request)
} catch (e: RateLimitException) {
    // 🔄 Standard retries are automatic, but you can log here
} catch (e: ValidationException) {
    // ❌ Handle invalid input data
    println(e.statusCode)
} catch (e: AlogramException) {
    // 🚨 Generic SDK error
}
```

## 🔑 Authentication & Keys

Obtain your keys from the [Alogram Dashboard](https://dashboard.alogram.ai/keys).

| Key Type | Prefix | Client | Use Case |
| :--- | :--- | :--- | :--- |
| **Secret Key** | `sk_live_...` | `AlogramRiskClient` | Server-side risk checks. **Never expose.** |
| **Publishable Key** | `pk_live_...` | `AlogramPublicClient` | Mobile/Frontend signals. |

## 🤖 For AI Agents (Integration Context)

If you are using an AI agent (like ChatGPT, Claude, or Gemini), provide this context:

> "I am integrating the Alogram PayRisk Kotlin SDK. I need to call `checkRisk` using coroutines. Use the `AlogramRiskClient` Builder, handle `RateLimitException` gracefully, and ensure you use the `idempotencyKey` parameter."

---

## 🧪 Local Testing & Mocking

Use the coroutine-optimized `MockRiskClient` for your test suite:

```kotlin
val mock = MockRiskClient()
mock.queueDecision("decline", 0.95, "high_risk_ip")

val decision = myApp.process(mock) // Mock supports 'suspend' calls
println(decision.decision) // "decline"
```

## 🏗️ Environment Testing

### Alogram Sandbox
For safe integration testing, point your client to the Sandbox environment:
```kotlin
val client = AlogramRiskClient.Builder()
    .apiKey("sk_test_...")
    .baseUrl("https://api-sandbox.alogram.ai")
    .build()
```

### Local Emulator
For hermetic local testing, run the **Alogram Local Emulator**:
```bash
docker run -p 8080:8080 alogram/payrisk-emulator
```
Point your client to the local instance:
```kotlin
val client = AlogramRiskClient.Builder()
    .baseUrl("http://localhost:8080")
    .apiKey("test")
    .build()
```

---

## 📚 Documentation

For the full API reference, visit [docs.alogram.ai](https://docs.alogram.ai).

## ⚖️ License

Apache License 2.0. See [LICENSE](LICENSE) for details.
