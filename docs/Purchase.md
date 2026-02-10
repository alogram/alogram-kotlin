
# Purchase

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **amount** | **kotlin.Float** | Value of the purchase in the specified currency. Must be a positive number with up to two decimal places.  |  |
| **currency** | **kotlin.String** | ISO 4217 currency code (e.g., &#39;USD&#39;). |  |
| **paymentMethod** | [**PaymentMethod**](PaymentMethod.md) |  |  |
| **locationId** | **kotlin.String** | Unique identifier for the location where the transaction occurred. |  [optional] |
| **deviceInfo** | [**DeviceInfo**](DeviceInfo.md) |  |  [optional] |
| **timestamp** | **kotlin.String** | RFC 3339 timestamp with timezone. |  [optional] |
| **transactionId** | **kotlin.String** | A unique identifier for the transaction. Must be between 3 and 50 characters and only contain alphanumeric characters, underscores, or hyphens.  |  [optional] |
| **channel** | [**ChannelEnum**](ChannelEnum.md) |  |  [optional] |
| **entryMethod** | [**EntryMethodEnum**](EntryMethodEnum.md) |  |  [optional] |
| **order** | [**OrderContext**](OrderContext.md) |  |  [optional] |
| **payerType** | [**PayerTypeEnum**](PayerTypeEnum.md) |  |  [optional] |
| **storedCredential** | [**StoredCredentialContext**](StoredCredentialContext.md) |  |  [optional] |
| **merchant** | [**MerchantContext**](MerchantContext.md) |  |  [optional] |
| **metadata** | **kotlin.String** | Optional key-value pairs providing additional context for the request.  Each key should be descriptive, and values should not exceed 2048 characters.  Each key should be descriptive.  |  [optional] |
