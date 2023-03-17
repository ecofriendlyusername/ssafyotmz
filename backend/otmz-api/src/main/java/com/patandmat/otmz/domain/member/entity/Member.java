package com.patandmat.otmz.domain.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column
    private String nickname;

    @Column
    private Long authId;

    @Column
    private String profileImg;

    @CreatedDate
    @Column
    private LocalDate joinDate; // 가입일자

    @ColumnDefault("0")
    private int isDeleted; // 탈퇴여부

    private String refreshToken;


    @Builder
    public Member(Long authId, String nickname, String profileImg){
        this.authId = authId;
        this.nickname = nickname;
        this.profileImg = profileImg;

    }


}
