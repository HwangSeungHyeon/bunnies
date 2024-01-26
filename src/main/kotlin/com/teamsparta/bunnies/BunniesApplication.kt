package com.teamsparta.bunnies

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@OpenAPIDefinition(servers = [Server(url = "https://2cfa-220-92-92-230.ngrok-free.app/", description = "Default Server URL")]) //https
@EnableJpaAuditing //AuditingEntityListener 기능 사용하기 위해서 추가
@SpringBootApplication
class BunniesApplication

fun main(args: Array<String>) {
    runApplication<BunniesApplication>(*args)
}
