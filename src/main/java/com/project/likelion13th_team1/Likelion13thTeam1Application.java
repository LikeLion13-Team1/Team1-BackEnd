package com.project.likelion13th_team1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class Likelion13thTeam1Application {

	public static void main(String[] args) {
		SpringApplication.run(Likelion13thTeam1Application.class, args);
	}

}
