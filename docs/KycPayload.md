
# KycPayload

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **provider** | **kotlin.String** |  |  [optional] |
| **documentType** | [**inline**](#DocumentType) | The document type used for verification. |  [optional] |
| **country** | **kotlin.String** |  |  [optional] |
| **result** | [**inline**](#Result) | The result of the KYC check. |  [optional] |
| **reasonCodes** | **kotlin.collections.List&lt;kotlin.String&gt;** | Array of reason codes for the KYC check. |  [optional] |
| **metadata** | **kotlin.String** | Optional key-value pairs providing additional context for the request.  Each key should be descriptive, and values should not exceed 2048 characters.  Each key should be descriptive.  |  [optional] |


<a id="DocumentType"></a>
## Enum: documentType
| Name | Value |
| ---- | ----- |
| documentType | passport, national_id, driver_license, other |


<a id="Result"></a>
## Enum: result
| Name | Value |
| ---- | ----- |
| result | passed, failed, review, timeout, error |
