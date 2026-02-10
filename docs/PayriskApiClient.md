# PayriskApiClient

All URIs are relative to *https://api-dev.alogram.ai/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**accountRiskCheck**](PayriskApiClient.md#accountRiskCheck) | **POST** v1/risk/account/check | Synchronous fraud decision for account/session events (signup, login, settings) |
| [**getFraudScores**](PayriskApiClient.md#getFraudScores) | **GET** v1/scores/{tenantId} | Retrieve fraud scores for a customer |
| [**healthCheck**](PayriskApiClient.md#healthCheck) | **GET** v1/health | Health check for the service |
| [**ingestPaymentEvent**](PayriskApiClient.md#ingestPaymentEvent) | **POST** v1/events | Ingest payment lifecycle events (authorization, capture, settlement, refund, dispute, chargeback, chargeback_outcome).  |
| [**ingestSignals**](PayriskApiClient.md#ingestSignals) | **POST** v1/signals | Ingest non-payment signals (account or interaction) for modeling |
| [**kycRiskCheck**](PayriskApiClient.md#kycRiskCheck) | **POST** v1/risk/kyc/check | Synchronous decision for KYC/identity verification |
| [**riskCheck**](PayriskApiClient.md#riskCheck) | **POST** v1/risk/check | Synchronous fraud decision for a purchase |



Synchronous fraud decision for account/session events (signup, login, settings)

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.alogram.payrisk.v1.models.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(PayriskApiClient::class.java)
val xIdempotencyKey : kotlin.String = xIdempotencyKey_example // kotlin.String | Unique Idempotency-Key sent in the POST request etc.
val accountCheckRequest : AccountCheckRequest =  // AccountCheckRequest | 
val xTraceId : kotlin.String = xTraceId_example // kotlin.String | Echoed or generated trace ID for tracking requests.

val result : DecisionResponse = webService.accountRiskCheck(xIdempotencyKey, accountCheckRequest, xTraceId)
```

### Parameters
| **xIdempotencyKey** | **kotlin.String**| Unique Idempotency-Key sent in the POST request etc. | |
| **accountCheckRequest** | [**AccountCheckRequest**](AccountCheckRequest.md)|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **xTraceId** | **kotlin.String**| Echoed or generated trace ID for tracking requests. | [optional] |

### Return type

[**DecisionResponse**](DecisionResponse.md)

### Authorization



### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json


Retrieve fraud scores for a customer

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.alogram.payrisk.v1.models.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(PayriskApiClient::class.java)
val tenantId : kotlin.String = tenantId_example // kotlin.String | 
val xTraceId : kotlin.String = xTraceId_example // kotlin.String | Echoed or generated trace ID for tracking requests.
val xIdempotencyKey : kotlin.String = xIdempotencyKey_example // kotlin.String | Unique Idempotency-Key sent in the GET request etc.
val startTime : kotlin.String = startTime_example // kotlin.String | 
val endTime : kotlin.String = endTime_example // kotlin.String | 
val pageSize : kotlin.Int = 56 // kotlin.Int | 
val pageToken : kotlin.String = pageToken_example // kotlin.String | 

val result : ScoresSuccessResponse = webService.getFraudScores(tenantId, xTraceId, xIdempotencyKey, startTime, endTime, pageSize, pageToken)
```

### Parameters
| **tenantId** | **kotlin.String**|  | |
| **xTraceId** | **kotlin.String**| Echoed or generated trace ID for tracking requests. | [optional] |
| **xIdempotencyKey** | **kotlin.String**| Unique Idempotency-Key sent in the GET request etc. | [optional] |
| **startTime** | **kotlin.String**|  | [optional] |
| **endTime** | **kotlin.String**|  | [optional] |
| **pageSize** | **kotlin.Int**|  | [optional] [default to 50] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pageToken** | **kotlin.String**|  | [optional] |

### Return type

[**ScoresSuccessResponse**](ScoresSuccessResponse.md)

### Authorization



### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json


Health check for the service

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.alogram.payrisk.v1.models.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(PayriskApiClient::class.java)

webService.healthCheck()
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


Ingest payment lifecycle events (authorization, capture, settlement, refund, dispute, chargeback, chargeback_outcome). 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.alogram.payrisk.v1.models.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(PayriskApiClient::class.java)
val xIdempotencyKey : kotlin.String = xIdempotencyKey_example // kotlin.String | Unique Idempotency-Key sent in the POST request etc.
val paymentEvent : PaymentEvent =  // PaymentEvent | 
val xTraceId : kotlin.String = xTraceId_example // kotlin.String | Echoed or generated trace ID for tracking requests.

webService.ingestPaymentEvent(xIdempotencyKey, paymentEvent, xTraceId)
```

### Parameters
| **xIdempotencyKey** | **kotlin.String**| Unique Idempotency-Key sent in the POST request etc. | |
| **paymentEvent** | [**PaymentEvent**](PaymentEvent.md)|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **xTraceId** | **kotlin.String**| Echoed or generated trace ID for tracking requests. | [optional] |

### Return type

null (empty response body)

### Authorization



### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/problem+json


Ingest non-payment signals (account or interaction) for modeling

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.alogram.payrisk.v1.models.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(PayriskApiClient::class.java)
val xIdempotencyKey : kotlin.String = xIdempotencyKey_example // kotlin.String | Unique Idempotency-Key sent in the POST request etc.
val signalsRequest : SignalsRequest =  // SignalsRequest | 
val xTraceId : kotlin.String = xTraceId_example // kotlin.String | Echoed or generated trace ID for tracking requests.

webService.ingestSignals(xIdempotencyKey, signalsRequest, xTraceId)
```

### Parameters
| **xIdempotencyKey** | **kotlin.String**| Unique Idempotency-Key sent in the POST request etc. | |
| **signalsRequest** | [**SignalsRequest**](SignalsRequest.md)|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **xTraceId** | **kotlin.String**| Echoed or generated trace ID for tracking requests. | [optional] |

### Return type

null (empty response body)

### Authorization



### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/problem+json


Synchronous decision for KYC/identity verification

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.alogram.payrisk.v1.models.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(PayriskApiClient::class.java)
val xIdempotencyKey : kotlin.String = xIdempotencyKey_example // kotlin.String | Unique Idempotency-Key sent in the POST request etc.
val kycCheckRequest : KycCheckRequest =  // KycCheckRequest | 
val xTraceId : kotlin.String = xTraceId_example // kotlin.String | Echoed or generated trace ID for tracking requests.

val result : DecisionResponse = webService.kycRiskCheck(xIdempotencyKey, kycCheckRequest, xTraceId)
```

### Parameters
| **xIdempotencyKey** | **kotlin.String**| Unique Idempotency-Key sent in the POST request etc. | |
| **kycCheckRequest** | [**KycCheckRequest**](KycCheckRequest.md)|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **xTraceId** | **kotlin.String**| Echoed or generated trace ID for tracking requests. | [optional] |

### Return type

[**DecisionResponse**](DecisionResponse.md)

### Authorization



### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json


Synchronous fraud decision for a purchase

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.alogram.payrisk.v1.models.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(PayriskApiClient::class.java)
val xIdempotencyKey : kotlin.String = xIdempotencyKey_example // kotlin.String | Unique Idempotency-Key sent in the POST request etc.
val checkRequest : CheckRequest =  // CheckRequest | 
val xTraceId : kotlin.String = xTraceId_example // kotlin.String | Echoed or generated trace ID for tracking requests.

val result : DecisionResponse = webService.riskCheck(xIdempotencyKey, checkRequest, xTraceId)
```

### Parameters
| **xIdempotencyKey** | **kotlin.String**| Unique Idempotency-Key sent in the POST request etc. | |
| **checkRequest** | [**CheckRequest**](CheckRequest.md)|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **xTraceId** | **kotlin.String**| Echoed or generated trace ID for tracking requests. | [optional] |

### Return type

[**DecisionResponse**](DecisionResponse.md)

### Authorization



### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json
