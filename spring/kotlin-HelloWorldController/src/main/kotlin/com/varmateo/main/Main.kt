package com.varmateo.main

import com.varmateo.controller.HelloWorldController
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import


@SpringBootApplication
@Import(value = [HelloWorldController::class])
class Main


fun main(args: Array<String>) {
    SpringApplication.run(Main::class.java, *args)
}
