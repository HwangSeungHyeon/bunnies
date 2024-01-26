package com.teamsparta.bunnies

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@PropertySource("classpath:server.yml")
@OpenAPIDefinition(servers = [Server(url = ("\${server}"), description = "Default Server URL")]) //https
@EnableJpaAuditing //AuditingEntityListener 기능 사용하기 위해서 추가
@SpringBootApplication
class BunniesApplication

fun main(args: Array<String>) {
    runApplication<BunniesApplication>(*args)
}
