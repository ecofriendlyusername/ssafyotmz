package com.patandmat.otmz.domain.member.entity;

import com.patandmat.otmz.domain.common.BaseEntity;
import com.patandmat.otmz.domain.item.entity.Item;
import com.patandmat.otmz.domain.item.entity.ItemMatch;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @Column(nullable = false, unique = true)
    private Long authId;

    @Column(nullable = false)
    private String profileImagePath;

    @Column(nullable = false)
    private boolean deleted; // 탈퇴여부

    private String refreshToken;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Item> items;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<ItemMatch> itemMatchBook;

    @Column(length = 2000)
    private String lookStyleStat;

    @Column(length = 2000)
    private String itemStyleStat;

    @Column(length = 2000)
    private String itemStyleStat;

    @Builder
    public Member(Long authId, String nickname, String profileImagePath) {
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
