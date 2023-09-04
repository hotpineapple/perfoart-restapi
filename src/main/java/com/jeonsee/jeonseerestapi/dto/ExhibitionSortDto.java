package com.jeonsee.jeonseerestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExhibitionSortDto {
    private String sortBy;
    private String sortOrder;
}
