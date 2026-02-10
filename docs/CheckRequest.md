
# CheckRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **entities** | [**EntityIds**](EntityIds.md) |  |  |
| **purchase** | [**Purchase**](Purchase.md) |  |  |
| **eventType** | [**inline**](#EventType) | (optional) event being checked, this may expand later beyond &#39;purchase&#39;. |  [optional] |
| **paymentIntentId** | **kotlin.String** | Server-minted unique payment identifier. |  [optional] |
| **identity** | [**Identity**](Identity.md) |  |  [optional] |


<a id="EventType"></a>
## Enum: eventType
| Name | Value |
| ---- | ----- |
| eventType | purchase |
