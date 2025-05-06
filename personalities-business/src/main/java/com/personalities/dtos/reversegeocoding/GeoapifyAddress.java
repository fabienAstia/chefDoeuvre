package com.personalities.dtos.reversegeocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeoapifyAddress(

        @JsonProperty("formatted") String formatted
//        @JsonProperty("address_line1") String addressLine1,
//        @JsonProperty("address_line2") String addressLine2
) {
}
