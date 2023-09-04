package com.jeonsee.jeonseerestapi.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection="alarm")
public class AlarmReqDto {
    private String token;
    private String keyword;

    public AlarmReqDto(){}
    public AlarmReqDto(String token, String keyword) {
        this.token = token;
        this.keyword = keyword;
    }
}
