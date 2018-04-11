/**
 *
 */
package com.varmateo.controller;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;


public final class GreetMessageResponse {


    private final String _input;
    private final String _output;


    @JsonCreator
    public GreetMessageResponse(
            @JsonProperty("name") String input,
            @JsonProperty("message") String output) {

        _input = input;
        _output = output;
    }


    @JsonProperty("name")
    public String input() {
        return _input;
    }


    @JsonProperty("message")
    public String output() {
        return _output;
    }

}
