package com.varmateo.controller

import com.varmateo.controller.GreetMessageResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloWorldController {

    @GetMapping(
            path = ["/hello"],
            produces = [MediaType.TEXT_PLAIN_VALUE])
    fun hello() = "Hello, world!"


    @GetMapping(
            path = ["/greetings"],
            produces = [MediaType.TEXT_PLAIN_VALUE])
    fun greetings(
            @RequestParam(value = "name", required = true)
            name: String) : String {

        return "Hello, ${name}!"
    }


    @GetMapping(
            path = ["/greet-message"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun greetMessage(
            @RequestParam(value = "input", required = true)
            input: String) : GreetMessageResponse {

        return GreetMessageResponse(
                input = input,
                output = "Hello, ${input.capitalize()}!")
    }

}
