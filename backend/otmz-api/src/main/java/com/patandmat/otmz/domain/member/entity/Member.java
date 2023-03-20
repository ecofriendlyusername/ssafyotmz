package com.patandmat.otmz.domain.member.entity;

import com.patandmat.otmz.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Long authId;

    @Column(nullable = false)
    private String profileImagePath;

    @Column(nullable = false)
    private boolean deleted; // 탈퇴여부

    private String refreshToken;

    @Builder
    public Member(Long authId, String nickname, String profileImagePath){
        this.authId = authId;
        this.nickname = nickname;
        this.profileImagePath = profileImagePath;
        this.deleted = false;
    }

    public void delete() {
        this.deleted = true;
    }

    public void restore() {
        this.deleted = true;
    }
}
