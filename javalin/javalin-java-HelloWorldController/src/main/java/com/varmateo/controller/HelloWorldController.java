/**
 *
 */
package com.varmateo.controller;

import io.javalin.Context;


public final class HelloWorldController {


    public static final String PATH_HELLO = "/hello";
    public static final String PATH_GREETINGS = "/greetings";
    public static final String PATH_GREET_MESSAGE = "/greet-message";

    private static final String PARAM_INPUT = "input";
    private static final String PARAM_NAME = "name";


    public HelloWorldController() {
        // Nothing to do.
    }


    public void hello(final Context context) {
        context.result("Hello, world!");
    }


    public void greetings(final Context context) {

        String name = context.queryParam(PARAM_NAME);

        if ( name != null ) {
            String response = String.format("Hello, %s", name);
            context.result(response);
        } else {
            context.status(400); // Bad request
        }
    }


    public void greetMessage(final Context context) {

        String input = context.queryParam(PARAM_INPUT);

        if ( input != null ) {
            GreetMessageResponse response = new GreetMessageResponse(
                    input,
                    String.format("Hello, %s", capitalize(input)));
            context.json(response);
        } else {
            context.status(400); // Bad request
        }
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

}
