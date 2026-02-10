
# ThreeDSData

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **version** | **kotlin.String** | The version of the 3D Secure protocol used (e.g., &#39;2.2.0&#39;). |  [optional] |
| **eci** | **kotlin.String** | Electronic Commerce Indicator. Values indicate the outcome of 3D Secure authentication.  |  [optional] |
| **flow** | [**inline**](#Flow) | The flow of the 3D Secure authentication. &#39;frictionless&#39; means no challenge was presented to the cardholder, &#39;challenge&#39; means the cardholder was challenged.  |  [optional] |
| **liabilityShift** | **kotlin.Boolean** | Indicates whether liability for chargebacks has shifted to the issuer (true) or remains with the merchant (false).  |  [optional] |
| **cavvPresent** | **kotlin.Boolean** | Indicates whether the Cardholder Authentication Verification Value (CAVV) was present in the 3DS message.  |  [optional] |


<a id="Flow"></a>
## Enum: flow
| Name | Value |
| ---- | ----- |
| flow | frictionless, challenge |
