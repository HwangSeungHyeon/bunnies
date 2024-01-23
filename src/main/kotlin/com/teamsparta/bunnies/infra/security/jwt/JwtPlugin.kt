package com.teamsparta.bunnies.infra.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*

@PropertySource("classpath:jwt.yml")
@Component
class JwtPlugin(
    @Value("\${issuer}") private val issuer: String,
    @Value("\${secret}") private val secret: String,
    @Value("\${accessTokenExpirationHour}") private val accessTokenExpirationHour: Long
) {
    //주어진 토큰을 검증하고 파싱(해석)하는 기능을 수행
    fun validateToken(jwt: String): Result<Jws<Claims>>{
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
        }
    }

    //토큰(Access Token)을 생성하는 역할
    fun generateAccessToken(subject: String, email: String, role: String): String {
        return generateToken(subject, email, role, Duration.ofHours(accessTokenExpirationHour))
    }


    //토큰(Access Token)을 실질적으로 생성하는 역할
    private fun generateToken(subject: String, email: String, role: String, expirationPeriod: Duration): String {
        val claims: Claims = Jwts.claims()
            .add(mapOf("role" to role, "email" to email))
            .build()

        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        //Keys.hmacShaKeyFor 메소드는 HMAC 알고리즘을 사용하는 키를 생성하는데, 주로 서명에 활용됨.

        val now = Instant.now()

        return Jwts.builder()
            .subject(subject) //토큰의 주체
            .issuer(issuer) //토큰의 발급자
            .issuedAt(Date.from(now)) //토큰의 발행 시간
            .expiration(Date.from(now.plus(expirationPeriod))) //토큰의 만료시간
            .claims(claims) //토큰의 추가정보
            .signWith(key) //토큰을 서명하기 위한 키 설정, 토큰이 유효한지를 검증
            .compact() //토큰 생성 후 문자열로 반환

        //이렇게 설정된 값들은 JWT의 헤더(Header), 페이로드(Payload), 서명(Signature)에 해당함.
    }
}