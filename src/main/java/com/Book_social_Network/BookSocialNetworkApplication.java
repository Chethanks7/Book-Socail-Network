package com.Book_social_Network;

import com.Book_social_Network.role.Role;
import com.Book_social_Network.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories
@EnableAsync
public class BookSocialNetworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookSocialNetworkApplication.class, args);
    }


    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("USER").isEmpty()) {
                roleRepository.save(Role
                        .builder()
                        .name("USER")
                        .build());
            }
        };
    }

}
