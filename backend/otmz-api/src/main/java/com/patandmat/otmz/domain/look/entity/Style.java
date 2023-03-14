package com.patandmat.otmz.domain.look.entity;

import lombok.Getter;

@Getter

public enum Style {

    TRADITIONAL("트레디셔널"),
    MANNISH("매니시"),
    FEMININE("페미닌"),
    ETHNIC("에스닉"),
    CONTEMPORARY("컨템포러리"),
    NATURAL("내추럴"),
    GENDER_FLUID("젠더프루이드"),
    SPORTY("스포티"),
    SUBCULTURE("서브컬쳐"),
    CASUAL("캐주얼");

    private final String value;

    Style(String value) {
        this.value = value;
    }
}
