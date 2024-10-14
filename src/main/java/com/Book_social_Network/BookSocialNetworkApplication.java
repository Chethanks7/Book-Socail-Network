package com.Book_social_Network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@EnableAsync
public class BookSocialNetworkApplication {

	public static void main(String[] args) {

		SpringApplication.run(BookSocialNetworkApplication.class, args);
	}

}
