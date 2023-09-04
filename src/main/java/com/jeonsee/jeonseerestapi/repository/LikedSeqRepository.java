package com.jeonsee.jeonseerestapi.repository;

import com.jeonsee.jeonseerestapi.dao.LikedSeq;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikedSeqRepository extends CrudRepository<LikedSeq, String> {}
