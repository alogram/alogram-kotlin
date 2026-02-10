# Alogram Payrisk Kotlin SDK

The official Kotlin client for the **Alogram Payments Risk API**. This SDK provides a robust, "smart" interface for checking fraud risk, ingesting behavioral signals, and managing payment lifecycle events, optimized for **Kotlin Coroutines**.

**Key Features:**
*   **Coroutines Native:** Fully asynchronous API using `suspend` functions.
*   **Resilient:** Built-in exponential backoff retries.
*   **Traceable:** Automatic injection of `x-trace-id` and `x-idempotency-key`.
*   **Observable:** First-class support for **OpenTelemetry** spans and attributes.
*   **Type-Safe:** Fully typed request/response models using Moshi.
*   **Secure:** Built-in webhook signature verification.

---

## 🏗️ Installation

### Gradle (Kotlin)
```kotlin
implementation("com.alogram:alogram-payrisk-kotlin:0.1.6-rc.2")
```

### Maven
```xml
<dependency>
    <groupId>com.alogram</groupId>
    <artifactId>alogram-payrisk-kotlin</artifactId>
    <version>0.1.6-rc.2</version>
</dependency>
```

---

## 🚀 Quickstart

### 1. Initialize the Client

```kotlin
import com.alogram.payrisk.AlogramRiskClient

val client = AlogramRiskClient.Builder()
    .baseUrl("https://api.alogram.ai")
    .apiKey("sk_live_...")
    .tenantId("your_tenant_id")
    .build()
```

### 2. Check Risk (Inside a Coroutine)

```kotlin
import com.alogram.payrisk.v1.models.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val request = CheckRequest(
        entities = EntityIds(tenantId = "tenant_123"),
        purchase = Purchase(
            amount = 99.00f, 
            currency = "USD", 
            paymentMethod = PaymentMethod(card = Card(type = "card", bin = "424242"))
        )
    )

    try {
        val response = client.checkRisk(request)
        println("Decision: ${response.decision}")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
```

---

## 📊 Observability (OpenTelemetry)

The SDK uses the standard OpenTelemetry Java/Kotlin API. It will automatically detect and use the `GlobalOpenTelemetry` instance.

**Captured Attributes:**
*   `alogram.idempotency_key`
*   `alogram.trace_id`
*   `alogram.decision`

---

## 🛡️ Webhook Security

Verify incoming webhooks using the built-in `WebhookVerifier`.

```kotlin
import com.alogram.payrisk.WebhookVerifier

val isValid = WebhookVerifier.verify(payloadBytes, signatureHeader, webhookSecret)
```

---

## ⚠️ Error Handling

| Exception | Description |
| :--- | :--- |
| `AuthenticationException` | Invalid API Key or Permissions. |
| `ValidationException` | Invalid request body or missing fields. |
| `RateLimitException` | Too many requests. **Automatically Retried.** |
| `InternalServerException` | Server-side issues. **Automatically Retried.** |

---

## 📦 License

Apache 2.0
