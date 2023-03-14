package com.patandmat.otmz.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15, unique = true)
    private String nickname;

    @Column
    private String authId;

    @Column(nullable = false, length = 3)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column
    private String profileImage;

    @Builder
    public Member(Long id, String nickname, String authId, Integer age, Gender gender, String profileImage) {
        this.id = id;
        this.nickname = nickname;
        this.authId = authId;
        this.age = age;
        this.gender = gender;
        this.profileImage = profileImage;
    }
}
