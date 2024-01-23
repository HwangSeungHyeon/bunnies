package com.teamsparta.bunnies

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing //AuditingEntityListener 기능 사용하기 위해서 추가
@SpringBootApplication
class BunniesApplication

fun main(args: Array<String>) {
    runApplication<BunniesApplication>(*args)
}
