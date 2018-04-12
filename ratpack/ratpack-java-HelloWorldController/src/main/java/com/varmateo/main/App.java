/**
 *
 */
package com.varmateo.main;

import io.vavr.Lazy;
import io.vavr.control.Try;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

import com.varmateo.controller.HelloWorldController;


public final class App {


    private static final int PORT = 8080;


    private final Lazy<RatpackServer> _server =
            Lazy.of(this::createServer);

    private final Lazy<HelloWorldController> _controller =
            Lazy.of(this::createController);



    public App() {
        // Nothing to do.
    }


    public void start() {

        try {
            _server.get().start();
        } catch ( Exception e ) {
            String message = String.format(
                    "Unable to start application - %s - %s",
                    e.getClass().getName(),
                    e.getMessage());
            throw new RuntimeException(message);
        }
    }


    private RatpackServer createServer() {

        return Try.of(this::doCreateRatpackServer).get();
    }


    private RatpackServer doCreateRatpackServer()
            throws Exception {

        HelloWorldController controller = _controller.get();
        ServerConfig serverConfig = ServerConfig.builder()
                .port(PORT)
                .build();
        Action<Chain> handlers = chain -> chain
                .get(
                        HelloWorldController.PATH_HELLO,
                        controller::hello)
                .get(
                        HelloWorldController.PATH_GREETINGS,
                        controller::greetings)
                .get(
                        HelloWorldController.PATH_GREET_MESSAGE,
                        controller::greetMessage);

        return RatpackServer.of(
                spec -> spec
                .serverConfig(serverConfig)
                .handlers(handlers));
    }


    private HelloWorldController createController() {

        return new HelloWorldController();
    }

}
