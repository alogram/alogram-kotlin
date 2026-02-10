
# IpInfo

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **ipAddress** | **kotlin.String** | IP address of the customer, client, or user that initiated the request. Each octet must be between 0 and 255.  |  [optional] |
| **ipv6** | **kotlin.String** | IPv6 address. |  [optional] |
| **ipVersion** | [**inline**](#IpVersion) | IP protocol version. |  [optional] |
| **ipPrefix** | **kotlin.String** | CIDR, e.g., 198.51.100.0/24 or 2001:db8::/48 |  [optional] |
| **asn** | **kotlin.String** | Autonomous System Number (ASN) of the IP address. |  [optional] |
| **org** | **kotlin.String** | Organization associated with the IP address. |  [optional] |
| **company** | **kotlin.String** | Company associated with the IP address. |  [optional] |
| **country** | **kotlin.String** | ISO 3166-1 alpha-2 country code. |  [optional] |
| **region** | **kotlin.String** | Region associated with the IP address. |  [optional] |
| **city** | **kotlin.String** | City associated with the IP address. |  [optional] |
| **postalCode** | **kotlin.String** | Postal code associated with the IP address. |  [optional] |


<a id="IpVersion"></a>
## Enum: ipVersion
| Name | Value |
| ---- | ----- |
| ipVersion | ipv4, ipv6 |
