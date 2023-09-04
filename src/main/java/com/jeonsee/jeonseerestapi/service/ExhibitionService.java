package com.jeonsee.jeonseerestapi.service;

import com.jeonsee.jeonseerestapi.dao.Exhibition;
import com.jeonsee.jeonseerestapi.dto.ExhibitionDto;
import com.jeonsee.jeonseerestapi.util.DocumentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExhibitionService {

    private final MongoTemplate mongoTemplate;
    private final LikeService likeService;
    private static final Pattern FREE_REGEX = Pattern.compile(".*무료.*");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public int getExhibitionCount(String from, String to, String realm, String area, String place, boolean isFree, String endAfter, String keyword) {
        Query query = new Query();

        if(from != null) query.addCriteria(Criteria.where("startDate").gte(from));
        if(to != null) query.addCriteria(Criteria.where("endDate").lte(to));
        if(realm != null) query.addCriteria(Criteria.where("realmName").is(realm));
        if(area != null) query.addCriteria(Criteria.where("area").is(area));
        if(place != null) query.addCriteria(Criteria.where("place").is(place));
        if(keyword != null) {
            List<Criteria> temp = Arrays.stream(keyword.split(" ")).map(word -> new Criteria().orOperator(
                    Criteria.where("title").regex(".*" + word + ".*", "i"),
                    Criteria.where("subTitle").regex(".*" + word + ".*", "i"),
                    Criteria.where("content1").regex(".*" + word + ".*", "i"),
                    Criteria.where("content2").regex(".*" + word + ".*", "i")
            )).collect(Collectors.toList());

            query.addCriteria(new Criteria().andOperator(temp));
        }
        if(isFree == true) query.addCriteria(Criteria.where("price").regex(FREE_REGEX));
        if(endAfter != null) query.addCriteria(Criteria.where("endDate").gte(Date.from(LocalDate.parse(endAfter, formatter).atStartOfDay(ZoneId.systemDefault()).toInstant())));

        return (int) mongoTemplate.count(query, Exhibition.class);
    }

    public List<String> getExhibitionRealmList() {
        List<String> temp = mongoTemplate.aggregate(
                Aggregation.newAggregation(
                        Aggregation.group("realmName")
                ),
                "exhibition",
                String.class
        ).getMappedResults().stream().map(str -> str.split(":")[1].replace("}","").replace("\"","")).collect(Collectors.toList());
        Collections.sort(temp);

        return temp;
    }

    public List<String> getExhibitionAreaList() {
        List<String> temp = mongoTemplate.aggregate(
                Aggregation.newAggregation(
                        Aggregation.group("area")
                ),
                "exhibition",
                String.class
        ).getMappedResults().stream().map(str -> str.split(":")[1].replace("}","").replace("\"","")).collect(Collectors.toList());
        Collections.sort(temp);

        return temp;
    }

    public List<String> getExhibitionPlaceList(String area) {
        List<String> temp = mongoTemplate.aggregate(
                Aggregation.newAggregation(
                        Aggregation.match(Criteria.where("area").is(area)),
                        Aggregation.group("place")
                ),
                "exhibition",
                String.class
        ).getMappedResults().stream().map(str -> str.split(":")[1].replace("}","").replace("\"","")).collect(Collectors.toList());
        Collections.sort(temp);

        return temp;
    }

    public List<ExhibitionDto> getExhibitionList(int page, int size, String from, String to, String realm, String area, String place, boolean isFree, String endAfter, String keyword, String sortBy, String sortOrder) {
        Sort sort = null;
        if (sortOrder.equals("asc")) sort = Sort.by(sortBy).ascending();
        else if (sortOrder.equals("desc")) sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Query query = new Query().with(pageable);
        if(from != null) query.addCriteria(Criteria.where("startDate").gte(from));
        if(to != null) query.addCriteria(Criteria.where("endDate").lte(to));
        if(realm != null) query.addCriteria(Criteria.where("realmName").is(realm));
        if(area != null) query.addCriteria(Criteria.where("area").is(area));
        if(place != null) query.addCriteria(Criteria.where("place").is(place));
        if(keyword != null) {
            List<Criteria> temp = Arrays.stream(keyword.split(" ")).map(word -> new Criteria().orOperator(
                    Criteria.where("title").regex(".*" + word + ".*", "i"),
               Criteria.where("subTitle").regex(".*" + word + ".*", "i"),
               Criteria.where("content1").regex(".*" + word + ".*", "i"),
               Criteria.where("content2").regex(".*" + word + ".*", "i")
            )).collect(Collectors.toList());

            query.addCriteria(new Criteria().andOperator(temp));
        }
        if(isFree == true) query.addCriteria(Criteria.where("price").regex(FREE_REGEX));
        if(endAfter != null) query.addCriteria(Criteria.where("endDate").gte(Date.from(LocalDate.parse(endAfter, formatter).atStartOfDay(ZoneId.systemDefault()).toInstant())));
//        System.out.println(query.toString());
        List<ExhibitionDto> result = mongoTemplate.find(query, Exhibition.class, "exhibition").stream()
                .map(exhibition -> new ExhibitionDto(exhibition, likeService.getLikeCount(String.valueOf(exhibition.getSeq()))))
                .collect(Collectors.toList());
        return result;
    }

    public List<ExhibitionDto> getExhibitionListByLike(int page, int size) {
        List<String> res = likeService.getSeqSortedByScoreDesc(page, size);
        return likeService.getSeqSortedByScoreDesc(page, size).stream()
                .map(seq -> getExhibitionDto(Integer.parseInt(seq)))
                .collect(Collectors.toList());
    }

    public ExhibitionDto getExhibitionDto(int seq) throws DocumentNotFoundException {
        Query query = new Query(Criteria.where("seq").is(seq));
        Exhibition exhibition = mongoTemplate.findOne(query, Exhibition.class);
        if(exhibition == null) throw new DocumentNotFoundException();
        return new ExhibitionDto(exhibition, likeService.getLikeCount(String.valueOf(seq)));
    }

}
