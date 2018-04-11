/**
 *
 */
package com.varmateo.main;

import io.javalin.Javalin;
import io.vavr.Lazy;

import com.varmateo.controller.HelloWorldController;


public final class App {


    private static final int PORT = 8080;


    private final Lazy<Javalin> _javalin =
            Lazy.of(this::createJavalin);

    private final Lazy<HelloWorldController> _controller =
            Lazy.of(this::createController);



    public App() {
        // Nothing to do.
    }


    public void start() {
        _javalin.get().start();
    }


    private Javalin createJavalin() {

        HelloWorldController controller = _controller.get();

        return Javalin.create()
                .port(PORT)
                .get(
                        HelloWorldController.PATH_HELLO,
                        controller::hello)
                .get(
                        HelloWorldController.PATH_GREETINGS,
                        controller::greetings)
                .get(
                        HelloWorldController.PATH_GREET_MESSAGE,
                        controller::greetMessage);
    }


    private HelloWorldController createController() {

        return new HelloWorldController();
    }

}
