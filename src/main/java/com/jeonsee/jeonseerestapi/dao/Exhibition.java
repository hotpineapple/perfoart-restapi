package com.jeonsee.jeonseerestapi.dao;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@Document(collection = "exhibition")
public class Exhibition {
    @Id
    private BigInteger _id;

    // 목록 조회
    private int seq;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String place;
    private String realmName;
    private String area;
    private String thumbnail;
    private float gpsX;
    private float gpsY;

    // 상세 조회
    private String subTitle;
    private String content1;
    private String content2;
    private String price;
    private String url;
    private String phone;
    private String imgUrl;
    private String placeUrl;
    private String placeAddr;
    private String placeSeq;

    // 추가정보
    private Date updateDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exhibition that = (Exhibition) o;
        return seq == that.seq;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

}

