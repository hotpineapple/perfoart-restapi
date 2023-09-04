package com.jeonsee.jeonseerestapi.dto;

import com.jeonsee.jeonseerestapi.dao.Exhibition;
import lombok.Getter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

@Getter
public class ExhibitionDto {
    private String id;

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
    private double likeCount;

    public ExhibitionDto() {}
    public ExhibitionDto(Exhibition exhibition, double likeCount) {
        id = exhibition.get_id().toString(16);
        seq = exhibition.getSeq();
        title = exhibition.getTitle();
        startDate = exhibition.getStartDate();
        endDate = exhibition.getEndDate();
        place = exhibition.getPlace();
        realmName = exhibition.getRealmName();
        area = exhibition.getArea();
        thumbnail = exhibition.getThumbnail();
        gpsX = exhibition.getGpsX();
        gpsY = exhibition.getGpsY();
        subTitle = exhibition.getSubTitle();
        content1 = exhibition.getContent1();
        content2 = exhibition.getContent2();
        price = exhibition.getPrice();
        url = exhibition.getUrl();
        phone = exhibition.getPhone();
        imgUrl = exhibition.getImgUrl();
        placeUrl = exhibition.getPlaceUrl();
        placeAddr = exhibition.getPlaceAddr();
        placeSeq = exhibition.getPlaceSeq();
        updateDate = exhibition.getUpdateDate();

        this.likeCount = likeCount;
    }
}
