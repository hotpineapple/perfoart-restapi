package com.jeonsee.jeonseerestapi.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@RedisHash("LikedSeq")
public class LikedSeq {
    @Id
    private String userEmail;
    private Set<String> seqSet;

    public LikedSeq(String userEmail) {
        this.userEmail = userEmail;
        seqSet = new HashSet<>();
    }

    public void addSeq(String seq) {
        this.seqSet.add(seq);
    }

    public void removeSeq(String seq) {
        this.seqSet.remove(seq);
    }

}
