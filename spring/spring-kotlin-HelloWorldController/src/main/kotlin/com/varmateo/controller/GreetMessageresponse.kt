package com.varmateo.controller

import com.fasterxml.jackson.annotation.JsonProperty


data class GreetMessageResponse(
        @JsonProperty("name") val input: String,
        @JsonProperty("message") val output: String
)
