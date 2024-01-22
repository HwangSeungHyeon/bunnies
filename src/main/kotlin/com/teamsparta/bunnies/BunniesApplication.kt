package com.teamsparta.bunnies

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BunniesApplication

fun main(args: Array<String>) {
    runApplication<BunniesApplication>(*args)

}
