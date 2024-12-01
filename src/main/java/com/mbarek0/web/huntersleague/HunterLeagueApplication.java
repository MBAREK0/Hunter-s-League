package com.mbarek0.web.huntersleague;

import com.mbarek0.web.huntersleague.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HunterLeagueApplication {

	public static void main(String[] args) {
		SpringApplication.run(HunterLeagueApplication.class, args);
	}

}
