package com.jeonsee.jeonseerestapi.service;

import com.jeonsee.jeonseerestapi.dto.ExhibitionFilterDto;
import com.jeonsee.jeonseerestapi.dto.ExhibitionSortDto;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.regex.Pattern;

@Service
public class ValidationService {
    private static final String DATE = "^\\d{4}-\\d{2}-\\d{2}$";
    private static final String COMMON_WORD = "^[가-힣]{1,100}$";
    private static final String SEARCH_WORD = "^[가-힣a-zA-Z\\s]{1,100}$";
    private static final String SORT_BY = "^(seq|title)$";
    private static final String SORT_ORDER = "^(asc|desc)$";
    private static final String OP = "^(enable|disable)$";

    public boolean isValid(ExhibitionFilterDto exhibitionFilterDto) {
//        System.out.println(exhibitionFilterDto.getArea());
        if(exhibitionFilterDto.getFrom() != null && !Pattern.matches(DATE, exhibitionFilterDto.getFrom())) return false;
        if(exhibitionFilterDto.getTo() != null && !Pattern.matches(DATE, exhibitionFilterDto.getTo())) return false;
        if(exhibitionFilterDto.getRealm() != null && !Pattern.matches(COMMON_WORD, exhibitionFilterDto.getRealm())) return false;
        if(exhibitionFilterDto.getArea() != null && !Pattern.matches(COMMON_WORD, exhibitionFilterDto.getArea())) return false;
//        if(exhibitionFilterDto.getPlace() != null && !Pattern.matches(SEARCH_WORD, exhibitionFilterDto.getPlace())) return false;
        if(exhibitionFilterDto.getEndAfter() != null && !Pattern.matches(DATE, exhibitionFilterDto.getEndAfter())) return false;
        if(exhibitionFilterDto.getKeyword() != null && !Pattern.matches(SEARCH_WORD ,exhibitionFilterDto.getKeyword())) return false;

        return true;
    }
    public boolean isValid(ExhibitionSortDto exhibitionSortDto) {
        if(!Pattern.matches(SORT_BY, exhibitionSortDto.getSortBy())) return false;
        if(!Pattern.matches(SORT_ORDER, exhibitionSortDto.getSortOrder())) return false;

        return true;
    }

    public boolean isValid(String op) {
        if(!Pattern.matches(OP, op)) return false;
        return true;
    }
}
