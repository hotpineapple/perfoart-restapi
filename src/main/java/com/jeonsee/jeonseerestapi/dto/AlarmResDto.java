package com.jeonsee.jeonseerestapi.dto;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class AlarmResDto {
    @Id
    private String id;
    private String userEmail;
    private String keyword;
    private boolean isEnabled;

    public AlarmResDto() {}
    public AlarmResDto(String id, String userEmail, String keyword, boolean isEnabled) {
        this.id = id;
        this.userEmail = userEmail;
        this.keyword = keyword;
        this.isEnabled = isEnabled;
    }
}
