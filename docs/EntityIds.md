
# EntityIds

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **tenantId** | **kotlin.String** | Canonical ID for the paying organization (Tenant). Opaque, immutable, lowercase.  Must start with \&quot;tid_\&quot;. Do not use domains or emails here. For a tenant&#39;s domain,  use a separate field (e.g., tenantDomain).  |  [optional] |
| **clientId** | **kotlin.String** | Canonical ID for the Tenant’s business customer (e.g., merchant/partner).  Opaque, immutable, lowercase. Must start with &#39;cid_&#39;.  |  [optional] |
| **endCustomerId** | **kotlin.String** | Canonical ID for the client’s end user / consumer (account holder).  Opaque, immutable, lowercase. Must start with \&quot;ecid_\&quot;.  Do not put PII (like emails or phone numbers) in this field.  |  [optional] |
| **memberId** | **kotlin.String** | Canonical ID for a Tenant member/operator (employee/contractor) using the platform.  Opaque, immutable, lowercase. Must start with &#39;mid_&#39;.  |  [optional] |
| **paymentInstrumentId** | **kotlin.String** | Tokenized instrument ID (non-PCI). |  [optional] |
| **deviceId** | **kotlin.String** | Server-issued stable device token (device-level identifier). Should persist across sessions and logins on the same browser/device.  |  [optional] |
| **sessionId** | **kotlin.String** | Application/user session identifier (login or checkout session). Typically rotates more frequently than deviceId and may be tied to authentication.  |  [optional] |
| **emailHash** | **kotlin.String** | Normalized+lowercased email hash (e.g., sha256). |  [optional] |
| **emailDomainHash** | **kotlin.String** | Normalized+lowercased email *domain* hash (e.g., sha256). |  [optional] |
| **phoneHash** | **kotlin.String** | Normalized+lowercased phone hash (e.g., sha256). |  [optional] |
| **shippingAddressHash** | **kotlin.String** | Normalized+lowercased shipping address hash (e.g., sha256). |  [optional] |
| **billingAddressHash** | **kotlin.String** | Normalized+lowercased billing address hash (e.g., sha256). |  [optional] |
