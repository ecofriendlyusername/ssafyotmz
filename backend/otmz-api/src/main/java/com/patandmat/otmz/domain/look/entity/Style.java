package com.patandmat.otmz.domain.look.entity;

import lombok.Getter;

@Getter
public enum Style {
    CLASSIC("classic", "클래식"),
    PREPPY("preppy", "프레피"),
    MANNISH("mannish", "매니시"),
    TOMBOY("tomboy", "톰보이"),
    FEMININE("feminine", "페미닌"),
    ROMANTIC("romantic", "로맨틱"),
    SEXY("sexy", "섹시"),
    HIPPIE("hippie", "히피"),
    WESTERN("western", "웨스턴"),
    ORIENTAL("oriental", "오리엔탈"),
    MODERN("modern", "모던"),
    SOPHISTICATED("sophisticated", "소피스트케이티드"),
    AVANTGARDE("avantgarde", "아방가르드"),
    COUNTRY("country", "컨트리"),
    RESORT("resort", "리조트"),
    GENDERLESS("genderless", "젠더리스"),
    SPORTY("sporty", "스포티"),
    RETRO("retro", "레트로"),
    HIPHOP("hiphop", "힙합"),
    KITSCH("kitsch", "키치"),
    PUNK("punk", "펑크"),
    STREET("street", "스트릿"),
    MILITARY("military", "밀리터리");

    private final String key;
    private final String name;

    Style(String key, String name) {
        this.key = key;
        this.name = name;
    }
}
