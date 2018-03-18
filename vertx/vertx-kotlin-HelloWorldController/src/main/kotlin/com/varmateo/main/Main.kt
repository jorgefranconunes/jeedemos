package com.varmateo.main

import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.web.Router

import com.varmateo.controller.HelloWorldController
import com.varmateo.controller.addRoute


const val SERVER_PORT = 8080


fun main(args: Array<String>) {
    App().start()
    println("Server has started!")
}


class App {

    private val httpServer: HttpServer by lazy {
        createHttpServer(vertx, router)
    }
    private val router: Router by lazy {
        createRouter(vertx, controller)
    }
    private val controller: HelloWorldController by lazy(::HelloWorldController)
    private val vertx: Vertx by lazy(Vertx::vertx)


    fun start() {
        httpServer.listen()
    }


    private fun createHttpServer(
            vertx: Vertx,
            router: Router): HttpServer {

        val options = HttpServerOptions().setPort(SERVER_PORT)

        return vertx
                .createHttpServer(options)
                .requestHandler(router::accept)
    }


    private fun createRouter(
            vertx: Vertx,
            controller: HelloWorldController): Router {

        val router = Router.router(vertx)

        router.addRoute(controller.hello)
        router.addRoute(controller.greetings)
        router.addRoute(controller.greetMessage);

        return router
    }
}
