package com.jeonsee.jeonseerestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExhibitionFilterDto {
    private String from;
    private String to;
    private String realm;
    private String area;
    private String place;
    private boolean isFree;
    private String endAfter;
    private String keyword;

}
