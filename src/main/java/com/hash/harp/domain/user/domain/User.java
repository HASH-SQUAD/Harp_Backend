package com.hash.harp.domain.user.domain;

import com.hash.harp.domain.user.domain.type.Authority;
import com.hash.harp.domain.user.domain.type.Gender;
import com.hash.harp.domain.user.domain.type.OauthType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String address;

    private Boolean isFirst;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private OauthType oauthType;

    @Builder
    public User(String username, String email, Gender gender, String address, Authority authority, Boolean isFirst, OauthType oauthType) {
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.authority = authority;
        this.isFirst = isFirst;
        this.oauthType = oauthType;
    }

    public User update() {
        this.isFirst = Boolean.FALSE;
        return this;
    }
}