
# AccountCheckRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **eventSubtype** | [**inline**](#EventSubtype) | The specific subtype of the account event (e.g., &#39;signup&#39;, &#39;login&#39;). |  |
| **entities** | [**EntityIds**](EntityIds.md) |  |  [optional] |
| **account** | [**Account**](Account.md) |  |  [optional] |
| **interaction** | [**Interaction**](Interaction.md) |  |  [optional] |


<a id="EventSubtype"></a>
## Enum: eventSubtype
| Name | Value |
| ---- | ----- |
| eventSubtype | signup, login, password_reset, mfa_reset, setting_change |
