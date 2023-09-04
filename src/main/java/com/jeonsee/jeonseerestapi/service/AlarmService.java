package com.jeonsee.jeonseerestapi.service;

import com.jeonsee.jeonseerestapi.dao.Alarm;
import com.jeonsee.jeonseerestapi.dto.AlarmReqDto;
import com.jeonsee.jeonseerestapi.dto.AlarmResDto;
import com.jeonsee.jeonseerestapi.util.ExceedLimitOfAlarmException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final MongoTemplate mongoTemplate;

    public void addAlarm(String email, String keyword) {
        Query query = new Query(Criteria.where("userEmail").is(email));
        List<Alarm> result = mongoTemplate.find(query, Alarm.class, "alarm");
        if (result.size() >= 10) throw new ExceedLimitOfAlarmException();

        Alarm alarm = new Alarm(email, keyword);
        mongoTemplate.save(alarm);
    }

    public List<AlarmResDto> getAlarm(String userEmail) {
        Query query = new Query(Criteria.where("userEmail").is(userEmail));
        return mongoTemplate.find(query, Alarm.class, "alarm").stream()
                .map(alarm -> new AlarmResDto(alarm.get_id().toString(16), alarm.getUserEmail(), alarm.getKeyword(), alarm.isEnabled())
        ).collect(Collectors.toList());
    }

    public void disableAlarm(String alarmId) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(alarmId)));
        Alarm alarm =  mongoTemplate.findOne(query, Alarm.class);

        if(alarm == null) throw new RuntimeException("");
        alarm.setEnabled(false);

        mongoTemplate.save(alarm);
    }

    public void enableAlarm(String alarmId) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(alarmId)));
        Alarm alarm =  mongoTemplate.findOne(query, Alarm.class);

        if(alarm == null) throw new RuntimeException("");
        alarm.setEnabled(true);

        mongoTemplate.save(alarm);
    }

    public void deleteAlarm(String alarmId) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(alarmId)), "alarm");
    }

    public void processAlarm(String alarmId, String op) {
        if(op.equals("enable")) enableAlarm(alarmId);
        else if(op.equals("disable")) disableAlarm(alarmId);
    }
}
