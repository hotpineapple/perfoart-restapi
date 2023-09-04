package com.jeonsee.jeonseerestapi.dao;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@Document(collection = "alarm")
public class Alarm {
    @Id
    private BigInteger _id;
    private String userEmail;
    private String keyword;
    private boolean isEnabled;

    public Alarm() {}
    public Alarm(String userEmail, String keyword) {
        this.userEmail = userEmail;
        this.keyword = keyword;
        this.isEnabled = true;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
