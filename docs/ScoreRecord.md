
# ScoreRecord

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **assessmentId** | **kotlin.String** | Universal decision identifier (for purchases, equals paymentIntentId). |  |
| **decisionAt** | **kotlin.String** | RFC 3339 timestamp with timezone. |  |
| **decision** | [**inline**](#Decision) | The synchronous risk decision for a purchase. |  |
| **riskScore** | **kotlin.Float** | Fraud risk score (0.00 - 1.00). |  |
| **paymentIntentId** | **kotlin.String** | Server-minted unique payment identifier. |  [optional] |
| **fraudScore** | [**FraudScore**](FraudScore.md) |  |  [optional] |
| **breakdown** | [**RiskBreakdown**](RiskBreakdown.md) |  |  [optional] |
| **reasons** | [**kotlin.collections.List&lt;ReasonDetail&gt;**](ReasonDetail.md) | Structured reason details for the score. |  [optional] |
| **entities** | [**EntityIds**](EntityIds.md) |  |  [optional] |
| **amount** | **kotlin.Float** | Value of the purchase in the specified currency. Must be a positive number with up to two decimal places.  |  [optional] |
| **currency** | **kotlin.String** | ISO 4217 currency code (e.g., &#39;USD&#39;). |  [optional] |


<a id="Decision"></a>
## Enum: decision
| Name | Value |
| ---- | ----- |
| decision | approve, review, decline, step_up |
