package com.patandmat.otmz.domain.auth.api.model;

import com.patandmat.otmz.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;


@Getter
public class MemberInfoFromKakao {
    private Long authId;

    private String nickname;

    private String profileImagePath;

    @Builder
    public MemberInfoFromKakao(Long authId, String nickname, String profileImagePath) {
        this.authId = authId;
        this.nickname = nickname;
        this.profileImagePath = profileImagePath;
    }

    public Member toEntity() {
        return Member.builder()
                .authId(this.authId)
                .nickname(this.nickname)
                .profileImagePath(this.profileImagePath)
                .build();
    }
}
