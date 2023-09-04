package com.jeonsee.jeonseerestapi.repository;

import com.jeonsee.jeonseerestapi.dao.LikedUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikedUserRepository extends CrudRepository<LikedUser, String> {}
