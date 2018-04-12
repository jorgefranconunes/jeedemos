/**
 *
 */
package com.varmateo.controller;

import ratpack.handling.Context;

import static ratpack.jackson.Jackson.json;


/**
 * This is just a dummy example.
 *
 * In the methods corresponding to endpoints it is not required to
 * return a response, through the received Context, before the method
 * returns. The response could be sent, through the received context,
 * from another thread that could execute after the endpoint method
 * had already returned.
 *
 * In fact, it is STRONGLY recommended for the endpoint methods to
 * return as soon as possible. Preparing the response, and sending the
 * response through the received Context should be done in another
 * thread.
 */
public final class HelloWorldController {


    public static final String PATH_HELLO = "hello";
    public static final String PATH_GREETINGS = "greetings";
    public static final String PATH_GREET_MESSAGE = "greet-message";

    private static final String PARAM_INPUT = "input";
    private static final String PARAM_NAME = "name";


    public HelloWorldController() {
        // Nothing to do.
    }


    public void hello(final Context context) {
        context.render("Hello, world!");
    }


    public void greetings(final Context context) {

        String name = context.getRequest().getQueryParams().get(PARAM_NAME);

        if ( name != null ) {
            String response = String.format("Hello, %s", name);
            context.render(response);
        } else {
            context.getResponse()
                    .status(400) // Bad request
                    .send();
        }
    }


    public void greetMessage(final Context context) {

        String input = context.getRequest().getQueryParams().get(PARAM_INPUT);

        if ( input != null ) {
            GreetMessageResponse response = new GreetMessageResponse(
                    input,
                    String.format("Hello, %s", capitalize(input)));
            context.render(json(response));
        } else {
            context.getResponse()
                    .status(400) // Bad request
                    .send();
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
