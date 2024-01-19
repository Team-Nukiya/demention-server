package team.nukiya.demention.infrastructure.client.address

import com.fasterxml.jackson.annotation.JsonProperty

data class AddressResponse(
    val documents: List<AddressElement>,
)

data class AddressElement(
    @JsonProperty("region_type")
    val regionType: String,
    @JsonProperty("address_name")
    val addressName: String,
    @JsonProperty("region_1depth_name")
    val sido: String,
    @JsonProperty("region_2depth_name")
    val gungu: String,
    @JsonProperty("region_3depth_name")
    val eupMyeonDong: String,
)
