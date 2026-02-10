
# DecisionResponse

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **assessmentId** | **kotlin.String** | Universal decision identifier. For purchases, equals paymentIntentId. |  |
| **decision** | [**inline**](#Decision) |  |  |
| **decisionAt** | **kotlin.String** | RFC 3339 timestamp with timezone. |  |
| **riskScore** | **kotlin.Float** |  |  |
| **fraudScore** | [**FraudScore**](FraudScore.md) |  |  [optional] |
| **breakdown** | [**RiskBreakdown**](RiskBreakdown.md) |  |  [optional] |
| **reasonCodes** | **kotlin.collections.List&lt;kotlin.String&gt;** | Technical reason codes for the decision. |  [optional] |
| **reasons** | [**kotlin.collections.List&lt;ReasonDetail&gt;**](ReasonDetail.md) | Structured reason details for the decision. |  [optional] |
| **actions** | [**inline**](#kotlin.collections.List&lt;Actions&gt;) |  |  [optional] |
| **paymentIntentId** | **kotlin.String** | Server-minted unique payment identifier. |  [optional] |
| **policyVersion** | **kotlin.String** | The version of the policy that generated the decision. |  [optional] |
| **modelVersion** | **kotlin.String** | The version of the model that generated the decision. |  [optional] |
| **ttlSeconds** | **kotlin.Int** | Time to live for the decision in seconds. |  [optional] |


<a id="Decision"></a>
## Enum: decision
| Name | Value |
| ---- | ----- |
| decision | approve, review, decline, step_up |


<a id="kotlin.collections.List<Actions>"></a>
## Enum: actions
| Name | Value |
| ---- | ----- |
| actions | step_up, throttle, lock_account, decline, queue_review |
