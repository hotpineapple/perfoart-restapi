package com.jeonsee.jeonseerestapi.service;

import com.jeonsee.jeonseerestapi.dao.LikedSeq;
import com.jeonsee.jeonseerestapi.dao.LikedUser;
import com.jeonsee.jeonseerestapi.repository.LikedSeqRepository;
import com.jeonsee.jeonseerestapi.repository.LikedUserRepository;
import com.jeonsee.jeonseerestapi.util.DocumentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikedUserRepository likedUserRepository;
    private final LikedSeqRepository likedSeqRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final String sortedSetName = "seqByCount";

    public List<String> getSeqSortedByScoreDesc(int page, int size) {
        long startIndex = (page - 1) * size;
        long endIndex = startIndex + size - 1;

        return new ArrayList<>(redisTemplate.opsForZSet().reverseRange(sortedSetName, startIndex, endIndex));
    }

    public void processLike(String op, String seq, String userEmail) {
        if(op.equals("add")) addLike(seq, userEmail);
        else if(op.equals("cancel")) cancelLike(seq, userEmail);
    }

    private void cancelLike(String seq, String userEmail) {
        LikedSeq likedSeq = likedSeqRepository.findById(userEmail).orElse(null);
        if(likedSeq == null) throw new DocumentNotFoundException();
        likedSeq.removeSeq(seq);
        if(likedSeq.getSeqSet().size() == 0) likedSeqRepository.delete(likedSeq);
        else likedSeqRepository.save(likedSeq);

        LikedUser likedUser = likedUserRepository.findById(seq).orElse(null);
        if(likedUser == null) throw new DocumentNotFoundException();
        likedUser.removeUserEmail(userEmail);
        if(likedUser.getUserEmailSet().size() == 0) likedUserRepository.delete(likedUser);
        else likedUserRepository.save(likedUser);

        Double currentScore = redisTemplate.opsForZSet().score(sortedSetName, seq);
        if(currentScore!= null && currentScore > 0) redisTemplate.opsForZSet().incrementScore(sortedSetName, seq, -1);
    }

    private void addLike(String seq, String userEmail) {
        LikedSeq likedSeq = likedSeqRepository.findById(userEmail).orElse(new LikedSeq(userEmail));
        likedSeq.addSeq(seq);
        likedSeqRepository.save(likedSeq);

        LikedUser likedUser = likedUserRepository.findById(seq).orElse(new LikedUser(seq));
        likedUser.addUserEmail(userEmail);
        likedUserRepository.save(likedUser);

        redisTemplate.opsForZSet().incrementScore(sortedSetName, seq, 1);
    }

    public Set<String> getLikedSeq(String userEmail) {
        LikedSeq likedSeq = likedSeqRepository.findById(userEmail).orElse(null);
        if(likedSeq != null) return likedSeq.getSeqSet();
        else return null;
    }

    public int getLikeCount(String seq) {
        LikedUser likedUser = likedUserRepository.findById(seq).orElse(null);
        if(likedUser != null) return likedUser.getUserEmailSet().size();
        else return 0;
    }

}
