package com.jeonsee.jeonseerestapi.controller;

import com.jeonsee.jeonseerestapi.dto.AlarmReqDto;
import com.jeonsee.jeonseerestapi.dto.AlarmResDto;
import com.jeonsee.jeonseerestapi.service.AlarmService;
import com.jeonsee.jeonseerestapi.service.AuthService;
import com.jeonsee.jeonseerestapi.service.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@AllArgsConstructor
@Validated
public class AlarmController {
    private final AlarmService alarmService;
    private final AuthService authService;
    private final ValidationService validationService;

    @PostMapping("/alarm")
    public ResponseEntity<String> addAlarm(@RequestBody AlarmReqDto alarm) throws IOException {
        alarmService.addAlarm(authService.getEmail(alarm.getToken()),alarm.getKeyword());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/alarm/{token}")
    public ResponseEntity<List<AlarmResDto>> getAlarm(@PathVariable String token) throws InvalidParameterException, IOException {
        return ResponseEntity.ok(alarmService.getAlarm(authService.getEmail(token)));
    }

    @PatchMapping("/alarm/{alarmId}")
    public ResponseEntity<String> disableAlarm(@PathVariable String alarmId, @RequestParam String op) {
        if(!validationService.isValid(op)) throw new InvalidParameterException();
        alarmService.processAlarm(alarmId, op);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/alarm/{alarmId}")
    public ResponseEntity<String> deleteAlarm(@PathVariable String alarmId) {
        alarmService.deleteAlarm(alarmId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
