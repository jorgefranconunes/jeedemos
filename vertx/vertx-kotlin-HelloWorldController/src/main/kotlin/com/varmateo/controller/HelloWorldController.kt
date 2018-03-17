package com.varmateo.controller

import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

import com.varmateo.controller.GreetMessageResponse


private const val PARAM_INPUT = "input"
private const val PARAM_NAME = "name"


class HelloWorldController {

    val hello = get("/hello", ::doHello)
    val greetings = get("/greetings", ::doGreetings)
    val greetMessage = get("/greet-message", ::doGreetMessage)


    private fun doHello(context: RoutingContext): Unit {
        context.response().end("Hello, world!")
    }


    fun doGreetings(context: RoutingContext): Unit {
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


    fun doGreetMessage(context: RoutingContext): Unit {
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
}


data class RouteDescription(
        val method: HttpMethod,
        val path: String,
        val handler: (RoutingContext) -> Unit)


fun Router.addRoute(routeDescription: RouteDescription): Router {

    this.route(routeDescription.path)
            .method(routeDescription.method)
            .handler(routeDescription.handler)

    return this
}


private fun get(
        path: String,
        handler: (RoutingContext) -> Unit)
        : RouteDescription = RouteDescription(HttpMethod.GET, path, handler)
