package com.onezerokang.cafe.auth.util

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtUtil(
    @Value("\${jwt.secret}")
    secretKey: String
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)
    private val key: Key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey))

    fun generateToken(subject: String, expiredAt: Date): String {
        return Jwts.builder()
            .setSubject(subject)
            .setExpiration(expiredAt)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (e: SecurityException) {
            log.info("Invalid JWT token", e)
            false
        } catch (e: MalformedJwtException) {
            log.info("Invalid JWT token", e)
            false
        } catch (e: ExpiredJwtException) {
            log.info("Expired JWT token", e)
            false
        } catch (e: UnsupportedJwtException) {
            log.info("Unsupported JWT token", e)
            false
        } catch (e: IllegalArgumentException) {
            log.info("JWT claims string is empty", e)
            false
        }
    }

    fun extractSubject(accessToken: String): String {
        val claims = parseClaims(accessToken)
        return claims.subject
    }

    private fun parseClaims(accessToken: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(accessToken)
            .body
    }
}