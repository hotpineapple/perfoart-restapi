package com.jeonsee.jeonseerestapi.controller;

import com.jeonsee.jeonseerestapi.dto.ExhibitionDto;
import com.jeonsee.jeonseerestapi.dto.LikeDto;
import com.jeonsee.jeonseerestapi.service.AuthService;
import com.jeonsee.jeonseerestapi.service.ExhibitionService;
import com.jeonsee.jeonseerestapi.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
public class LikeController {
    private final LikeService likeService;
    private final ExhibitionService exhibitionService;
    private final AuthService authService;

    @PatchMapping("/like")
    public void likeExhibition(@RequestBody LikeDto likeDto) throws InvalidParameterException, IOException {
        likeService.processLike(likeDto.getOp(), likeDto.getSeq(), authService.getEmail(likeDto.getToken()));
    }

    @GetMapping("/like/{token}")
    public ResponseEntity<List<ExhibitionDto>> getLikedExhibition(@PathVariable String token) throws InvalidParameterException, IOException {
        Set<String> likedSet = likeService.getLikedSeq(authService.getEmail(token));

        if(likedSet == null) return ResponseEntity.ok(null);
        else return ResponseEntity.ok(likedSet.stream()
                .map(likedSeq -> exhibitionService.getExhibitionDto(Integer.parseInt(likedSeq)))
                .collect(Collectors.toList()));
    }

    @GetMapping("/like-count/{seq}")
    public ResponseEntity<Integer> getLikedCount(@PathVariable String seq) {
        return ResponseEntity.ok(likeService.getLikeCount(seq));
    }
}
