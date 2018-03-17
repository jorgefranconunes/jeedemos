package com.varmateo.main

import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.web.Router

import com.varmateo.controller.HelloWorldController
import com.varmateo.controller.addRoute


const val SERVER_PORT = 8080


fun main(args: Array<String>) {

    val vertx: Vertx = Vertx.vertx()
    val server: HttpServer = createHttpServer(vertx)
            .listen()

    println("Server has started!")
}


fun createHttpServer(vertx: Vertx): HttpServer {

    val options: HttpServerOptions = HttpServerOptions()
            .setPort(SERVER_PORT)
    val router: Router = createRouter(vertx)

    return vertx.createHttpServer(options)
            .requestHandler(router::accept)
}


fun createRouter(vertx: Vertx): Router {

    val controller = HelloWorldController()
    val router = Router.router(vertx)

    router.addRoute(controller.hello)
    router.addRoute(controller.greetings)
    router.addRoute(controller.greetMessage);

    return router
}
