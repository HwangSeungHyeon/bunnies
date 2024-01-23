package com.teamsparta.bunnies

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
class BunniesApplication

fun main(args: Array<String>) {
    runApplication<BunniesApplication>(*args)

}
