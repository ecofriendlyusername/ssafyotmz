package com.patandmat.otmz.domain.member.api.model;

import com.patandmat.otmz.domain.look.api.model.LookCountDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MypageDto {

    private String nickname;
    private int totalStyleCount;
    private int totalItemCount;

    private List<LookCountDto> lookCountDtoList;



}
