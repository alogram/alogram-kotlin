
# KycCheckRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **eventSubtype** | [**inline**](#EventSubtype) | The specific subtype of the KYC event. |  |
| **account** | [**Account**](Account.md) |  |  |
| **entities** | [**EntityIds**](EntityIds.md) |  |  [optional] |
| **kyc** | [**KycPayload**](KycPayload.md) |  |  [optional] |


<a id="EventSubtype"></a>
## Enum: eventSubtype
| Name | Value |
| ---- | ----- |
| eventSubtype | pre_kyc_check, doc_scan, liveness, address_check, sanctions_pep |
