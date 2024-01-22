package com.teamsparta.bunnies.infra.security.jwt

import com.teamsparta.bunnies.infra.security.UserPrincipal
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetails
import java.io.Serializable

class JwtAuthenticationToken(
    private val principal: UserPrincipal,
    // 요청한 Address 정보, sessionId등을 담음 (로깅 용도)
    details: WebAuthenticationDetails,
) : AbstractAuthenticationToken(principal.authorities), Serializable {

    init {
        super.setAuthenticated(true)
        super.setDetails(details)
    }

    override fun getPrincipal() = principal

    override fun getCredentials() = null

    override fun isAuthenticated(): Boolean {
        return true
    }

}