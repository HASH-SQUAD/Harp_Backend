package com.hash.harp.global.jwt.util;


import com.hash.harp.global.jwt.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties jwtProperties;

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(jwtProperties.getHeader());

        return parseToken(bearer);
    }

    public String replaceBearer(String bearer) {
        if (bearer == null || bearer.isEmpty()) return null;
        return bearer.replaceAll(jwtProperties.getPrefix(), "").trim();
    }

    public String parseToken(String bearer) {
        String token = replaceBearer(bearer);
        if (token != null) {
            return token;
        }
        return null;
    }

    public Jws<Claims> getJwt(String token) {
        if (token == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        return Jwts.parserBuilder().setSigningKey(jwtProperties.getSecret()).build().parseClaimsJws(token);
    }

    public Claims getJwtBody(String bearer) {
        Jws<Claims> jwt = getJwt(parseToken(bearer));
        return jwt.getBody();
    }
}
