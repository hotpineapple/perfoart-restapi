package com.jeonsee.jeonseerestapi.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@RedisHash("LikedUser")
public class LikedUser implements Serializable {
    @Id
    private String seq;
    private Set<String> userEmailSet;

    public LikedUser(String seq) {
        this.seq = seq;
        this.userEmailSet = new HashSet<>();
    }

    public void addUserEmail(String userEmail) {
        this.userEmailSet.add(userEmail);
    }

    public void removeUserEmail(String userEmail) {
        this.userEmailSet.remove(userEmail);
    }

}
