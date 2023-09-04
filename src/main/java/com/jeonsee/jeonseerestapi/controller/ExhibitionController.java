package com.jeonsee.jeonseerestapi.controller;

import com.jeonsee.jeonseerestapi.dto.ExhibitionDto;
import com.jeonsee.jeonseerestapi.dto.ExhibitionFilterDto;
import com.jeonsee.jeonseerestapi.dto.ExhibitionSortDto;
import com.jeonsee.jeonseerestapi.service.ExhibitionService;
import com.jeonsee.jeonseerestapi.service.ValidationService;
import com.jeonsee.jeonseerestapi.util.DocumentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
public class ExhibitionController {
    private final ExhibitionService exhibitionService;
    private final ValidationService validationService;

    @GetMapping("/exhibition-count")
    public ResponseEntity<Integer> getCount(
            @RequestParam(value="from", required = false) String from,
            @RequestParam(value="to", required = false) String to,
            @RequestParam(value="realm", required = false) String realm,
            @RequestParam(value="area", required = false) String area,
            @RequestParam(value="place", required = false) String place,
            @RequestParam(value="isFree", required = false) boolean isFree,
            @RequestParam(value="endAfter", required = false) String endAfter,
            @RequestParam(value="keyword", required = false) String keyword
    ) {
        if(!validationService.isValid(new ExhibitionFilterDto(from,to,realm,area,place,isFree,endAfter,keyword))) throw new InvalidParameterException();

        return ResponseEntity.ok(exhibitionService.getExhibitionCount(from, to, realm, area, place, isFree, endAfter, keyword));
    }

    @GetMapping("/exhibition-realm")
    public ResponseEntity<List<String>> getRealm() {
        return ResponseEntity.ok((List<String>) exhibitionService.getExhibitionRealmList());
    }

    @GetMapping("/exhibition-area")
    public ResponseEntity<List<String>> getArea() {
        return ResponseEntity.ok((List<String>) exhibitionService.getExhibitionAreaList());
    }

    @GetMapping("/exhibition-place")
    public ResponseEntity<List<String>> getPlaceByArea(@RequestParam String area) {
        return ResponseEntity.ok((List<String>) exhibitionService.getExhibitionPlaceList(area));
    }

    @GetMapping("/exhibition")
    public ResponseEntity<List<ExhibitionDto>> getExhibitionList(
            @RequestParam(value="page", defaultValue = "0") @Min(0) int page,
            @RequestParam(value="size", defaultValue = "10") @Max(1000) @Min(1) int size,
            @RequestParam(value="from", required = false) String from,
            @RequestParam(value="to", required = false) String to,
            @RequestParam(value="realm", required = false) String realm,
            @RequestParam(value="area", required = false) String area,
            @RequestParam(value="place", required = false) String place,
            @RequestParam(value="isFree", required = false) boolean isFree,
            @RequestParam(value="endAfter", required = false) String endAfter,
            @RequestParam(value="keyword", required = false) String keyword,
            @RequestParam(value="sortBy", defaultValue = "seq") String sortBy,
            @RequestParam(value="sortOrder", defaultValue = "desc") String sortOrder
    ) {
        if(!validationService.isValid(new ExhibitionFilterDto(from,to,realm,area,place,isFree,endAfter,keyword)) ||
                !validationService.isValid(new ExhibitionSortDto(sortBy, sortOrder))) throw new InvalidParameterException();

        return ResponseEntity.ok(exhibitionService.getExhibitionList(page, size, from, to, realm, area, place, isFree, endAfter, keyword, sortBy, sortOrder));
    }

    @GetMapping("/exhibition-hot")
    public ResponseEntity<List<ExhibitionDto>> getExhibitionList(
            @RequestParam(value="page", defaultValue = "1") @Min(1) int page,
            @RequestParam(value="size", defaultValue = "5") @Max(1000) @Min(1) int size
    ) {
        return ResponseEntity.ok(exhibitionService.getExhibitionListByLike(page, size));
    }

    @GetMapping("/exhibition/{seq}")
    public ResponseEntity<ExhibitionDto> getExhibition(@PathVariable int seq) throws DocumentNotFoundException {
        return ResponseEntity.ok(exhibitionService.getExhibitionDto(seq));
    }

}
