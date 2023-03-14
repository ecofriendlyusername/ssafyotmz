package com.patandmat.otmz.domain.member.entity;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("남자"),
    FEMALE("여자"),
    PRIVATE("비공개");

    private final String value;

    Gender(String value){
        this.value = value;
    }

}
