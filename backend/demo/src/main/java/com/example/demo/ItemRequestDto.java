package com.example.demo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemRequestDto {
    private String name;
    private String category;
    private String categoryVector;
    private String printVector;
    private String fabricVector;
}
