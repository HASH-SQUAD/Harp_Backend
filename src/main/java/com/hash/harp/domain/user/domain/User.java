package com.hash.harp.domain.user.domain;

import com.hash.harp.domain.user.domain.type.Authority;
import com.hash.harp.domain.user.domain.type.Gender;
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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;

    private Boolean isFirst;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public User(String username, String email, Gender gender, String address, Authority authority, Boolean isFirst) {
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.authority = authority;
        this.isFirst = isFirst;
    }

    public User update() {
        this.isFirst = Boolean.FALSE;
        return this;
    }
}
