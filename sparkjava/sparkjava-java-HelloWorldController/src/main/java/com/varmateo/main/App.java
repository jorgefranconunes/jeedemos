/**
 *
 */
package com.varmateo.main;

import spark.Service;
import io.vavr.Lazy;

import com.varmateo.controller.HelloWorldController;


public final class App {


    private static final int PORT = 8080;


    private final Lazy<Service> _server =
            Lazy.of(this::createServer);

    private final Lazy<HelloWorldController> _controller =
            Lazy.of(this::createController);



    public App() {
        // Nothing to do.
    }


    public void start() {
        _server.get();
    }


    private Service createServer() {

        HelloWorldController controller = _controller.get();
        Service server = Service.ignite()
                .port(PORT);

        server.get(
                HelloWorldController.PATH_HELLO,
                controller::hello);
        server.get(
                HelloWorldController.PATH_GREETINGS,
                controller::greetings);
        server.get(
                HelloWorldController.PATH_GREET_MESSAGE,
                controller::greetMessage);

        return server;
    }


    private HelloWorldController createController() {

        return new HelloWorldController();
    }

}
