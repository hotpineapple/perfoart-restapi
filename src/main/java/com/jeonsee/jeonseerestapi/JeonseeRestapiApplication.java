package com.jeonsee.jeonseerestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
public class JeonseeRestapiApplication {
	public static void main(String[] args) {
		SpringApplication.run(JeonseeRestapiApplication.class, args);
	}

}
