
# OrderContext

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **orderId** | **kotlin.String** | Unique identifier for the order. |  [optional] |
| **orderTotal** | **kotlin.Float** | Value of the purchase in the specified currency. Must be a positive number with up to two decimal places.  |  [optional] |
| **shippingMethod** | [**inline**](#ShippingMethod) | Shipping method for the order. |  [optional] |
| **lineItemCount** | **kotlin.Int** | Number of items in the order. |  [optional] |


<a id="ShippingMethod"></a>
## Enum: shippingMethod
| Name | Value |
| ---- | ----- |
| shippingMethod | digital, ship, bopis |
