package com.varmateo.main

import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

import com.varmateo.controller.GreetMessageResponse


const val SERVER_PORT = 8080

const val PARAM_NAME = "name"
const val PARAM_INPUT = "input"


fun main(args: Array<String>) {

    val vertx: Vertx = Vertx.vertx()
    val server: HttpServer = createHttpServer(vertx)
            .listen()

    println("Hello, world!")
}


fun createHttpServer(vertx: Vertx): HttpServer {

    val options: HttpServerOptions = HttpServerOptions()
            .setPort(SERVER_PORT)
    val router: Router = createRouter(vertx)

    return vertx.createHttpServer(options)
            .requestHandler(router::accept)
}


fun createRouter(vertx: Vertx): Router {

    val router = Router.router(vertx)

    router.route("/hello").handler(::helloHandler)
    router.route("/greetings").handler(::greetingsHandler)
    router.route("/greet-message").handler(::greetMessageHandler)

    return router
}


fun helloHandler(context: RoutingContext): Unit {
    context.response().end("Hello, world!")
}


fun greetingsHandler(context: RoutingContext): Unit {
    val name: String? = context.request().getParam(PARAM_NAME)
    val response: HttpServerResponse = context.response()

    if (name != null) {
        response
                .setChunked(true)
                .write("Hello, ${name}")
    } else {
        response.setStatusCode(400)
    }

    response.end()
}


fun greetMessageHandler(context: RoutingContext): Unit {
    var input: String? = context.request().getParam(PARAM_INPUT)
    var response: HttpServerResponse = context.response()

    if (input != null) {
        var result = GreetMessageResponse(
                input = input,
                output = "Hello, ${input.capitalize()}!")

        response
                .setChunked(true)
                .putHeader("content-type", "application/json; charset=utf-8")
                .write(Json.encodePrettily(result))
    } else {
        response.setStatusCode(400)
    }

    response.end()
}
