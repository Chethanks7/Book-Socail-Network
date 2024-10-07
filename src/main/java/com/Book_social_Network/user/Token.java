package com.Book_social_Network.user;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String token ;
    private LocalDateTime expiresAt ;
    private String validatedAt ;
    private String createdAt ;

    @ManyToOne
    @JoinColumn(name = "user-id", nullable = false)
    private User user ; 

}