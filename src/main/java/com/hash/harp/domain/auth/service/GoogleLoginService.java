package com.hash.harp.domain.auth.service;

import com.hash.harp.domain.user.domain.User;
import com.hash.harp.domain.user.domain.type.Authority;
import com.hash.harp.domain.user.domain.type.OauthType;
import com.hash.harp.domain.user.repository.UserRepository;
import com.hash.harp.global.config.properties.AuthProperties;
import com.hash.harp.global.feign.GoogleAuthClient;
import com.hash.harp.global.feign.GoogleInfoClient;
import com.hash.harp.global.feign.dto.GoogleInfoResponse;
import com.hash.harp.global.feign.dto.GoogleTokenRequest;
import com.hash.harp.global.jwt.dto.TokenResponse;
import com.hash.harp.global.jwt.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleLoginService {

    private final GoogleAuthClient googleAuthClient;
    private final GoogleInfoClient googleInfoClient;
    private final AuthProperties authProperties;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public TokenResponse login(String code) {
        String googleToken = googleAuthClient.getGoogleToken(createRequest(code)).accessToken();
        GoogleInfoResponse userInfo = googleInfoClient.getUserInfo(googleToken);
        User user = saveOrUpdate(userInfo);
        return jwtProvider.generateToken(user.getEmail(), user.getAuthority().toString());
    }


    private GoogleTokenRequest createRequest(String code) {
        return GoogleTokenRequest.builder()
                .code(code)
                .clientId(authProperties.getClientId())
                .clientSecret(authProperties.getClientSecret())
                .redirectUri(authProperties.getRedirectUri())
                .grantType("authorization_code")
                .build();
    }

    private User saveOrUpdate(GoogleInfoResponse userInfo) {
        Optional<User> user = userRepository.findByEmail(userInfo.email());

        if (user.isEmpty()) {
            return userRepository.save(User.builder()
                    .email(userInfo.email())
                    .username(userInfo.name())
                    .authority(Authority.USER)
                    .isFirst(Boolean.TRUE)
                    .oauthType(OauthType.GOOGLE)
                    .build());
        }

        return user.get().update();
    }
}
