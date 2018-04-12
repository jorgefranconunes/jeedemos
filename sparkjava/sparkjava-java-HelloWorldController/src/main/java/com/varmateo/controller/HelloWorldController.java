/**
 *
 */
package com.varmateo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

import java.io.IOException;


public final class HelloWorldController {


    public static final String PATH_HELLO = "/hello";
    public static final String PATH_GREETINGS = "/greetings";
    public static final String PATH_GREET_MESSAGE = "/greet-message";

    private static final String PARAM_INPUT = "input";
    private static final String PARAM_NAME = "name";

    private final ObjectMapper _objectMapper;


    public HelloWorldController() {

        _objectMapper = new ObjectMapper();
    }


    public String hello(
            final Request request,
            final Response response) {

        return "Hello, world!";
    }


    public String greetings(
            final Request request,
            final Response response) {

        String name = request.queryParams(PARAM_NAME);
        final String result;

        if ( name != null ) {
            result = String.format("Hello, %s", name);
        } else {
            response.status(400); // Bad request
            result = "";
        }

        return result;
    }


    public String greetMessage(
            final Request request,
            final Response response)
            throws IOException {

        String input = request.queryParams(PARAM_INPUT);
        final String result;

        if ( input != null ) {
            GreetMessageResponse endpointResponse = new GreetMessageResponse(
                    input,
                    String.format("Hello, %s", capitalize(input)));
            response.type("application/json");
            result = json(endpointResponse);
        } else {
            response.status(400); // Bad request
            result = "";
        }

        return result;
    }


    private String capitalize(final String s) {

        if ( s.length() == 0 ) {
            return s;
        } else {
            return new StringBuilder()
                    .append(Character.toUpperCase(s.charAt(0)))
                    .append(s.substring(1).toLowerCase())
                    .toString();
        }
    }


    private String json(final Object object)
            throws IOException {

        return _objectMapper.writeValueAsString(object);
    }

}
