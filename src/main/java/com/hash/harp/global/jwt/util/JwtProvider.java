package com.hash.harp.global.jwt.util;

import com.hash.harp.global.jwt.dto.TokenResponse;
import com.hash.harp.global.jwt.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

import static com.hash.harp.global.jwt.properties.JwtConstants.*;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;

    public String generateAccessToken(String authId, String role) {
        return generateToken(authId, role, ACCESS_KEY.getMessage(), jwtProperties.getAccessExp());
    }

    public TokenResponse generateToken(String authId, String role) {

        String accessToken = generateToken(authId, role, ACCESS_KEY.getMessage(), jwtProperties.getAccessExp());
        String refreshToken = generateToken(authId, role, REFRESH_KEY.getMessage(), jwtProperties.getRefreshExp());

        return new TokenResponse(accessToken, refreshToken, getExpiredTime());
    }

    private String generateToken(String authId, String role, String type, Long exp) {
        return Jwts.builder()
                .setHeaderParam(TYPE.message, type)
                .claim(ROLE.getMessage(), role)
                .claim(AUTH_ID.getMessage(), authId)
                .signWith(jwtProperties.getSecret(), SignatureAlgorithm.HS256)
                .setExpiration(
                        new Date(System.currentTimeMillis() + exp * 1000)
                )
                .compact();
    }

    public ZonedDateTime getExpiredTime() {
        return ZonedDateTime.now().plusSeconds(jwtProperties.getRefreshExp());
    }
}
