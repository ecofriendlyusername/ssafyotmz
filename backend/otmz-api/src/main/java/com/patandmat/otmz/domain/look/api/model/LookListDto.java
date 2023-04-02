package com.patandmat.otmz.domain.look.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LookListDto {

    int totalStyle;
    String nickname;
    Page<LookResponseDto> page;

}
