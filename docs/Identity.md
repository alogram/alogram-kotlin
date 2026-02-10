
# Identity

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **email** | **kotlin.String** | Email address of the customer. |  [optional] |
| **emailDomain** | **kotlin.String** | Email domain only (no local part). Useful for domain-level risk and allow/deny lists without sending full email PII. Example: \&quot;gmail.com\&quot;, \&quot;merchant-example.co.uk\&quot;  |  [optional] |
| **phone** | **kotlin.String** | Phone number that supports international E.164 format, as well as spaces, dashes, and parentheses.  Examples: \&quot;+1 (415) 555-2671\&quot;, \&quot;415-555-2671\&quot;, \&quot;+14155552671\&quot;  |  [optional] |
| **shippingAddress** | [**PostalAddress**](PostalAddress.md) |  |  [optional] |
| **billingAddress** | [**PostalAddress**](PostalAddress.md) |  |  [optional] |
