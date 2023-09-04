package com.jeonsee.jeonseerestapi.dto;

import javax.validation.constraints.Pattern;

public class LikeDto {
    private String seq;
    private String token;
    @Pattern(regexp = "^(add|cancel)$")
    private String op;

    public LikeDto(){}
    public LikeDto(String seq, String token, String op) {
        this.seq = seq;
        this.token = token;
        this.op = op;
    }

    public String getSeq() {
        return seq;
    }

    public String getToken() {
        return token;
    }

    public String getOp() {
        return op;
    }
}
