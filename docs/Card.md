
# Card

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **type** | [**inline**](#Type) | Fixed to &#x60;card&#x60; for this schema. |  |
| **cardNetwork** | [**CardNetworkEnum**](CardNetworkEnum.md) |  |  [optional] |
| **cardType** | [**PaymentCardTypeEnum**](PaymentCardTypeEnum.md) |  |  [optional] |
| **bin** | **kotlin.String** | Bank Identification Number (IIN). First 6–8 digits of the PAN; do not send full PAN. |  [optional] |
| **issuerCountry** | **kotlin.String** | ISO 3166-1 alpha-2 country code. |  [optional] |
| **avsResult** | [**AvsResultEnum**](AvsResultEnum.md) |  |  [optional] |
| **cvvResult** | [**CvvResultEnum**](CvvResultEnum.md) |  |  [optional] |
| **scaMethod** | [**ScaMethodEnum**](ScaMethodEnum.md) |  |  [optional] |
| **threeDS** | [**ThreeDSData**](ThreeDSData.md) |  |  [optional] |


<a id="Type"></a>
## Enum: type
| Name | Value |
| ---- | ----- |
| type | card |
