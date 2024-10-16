package com.Book_social_Network.book;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String author;
    private String isbn;
    private String bookCover;
    private boolean archived;
    private boolean sharable;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModificationDate;

    @CreatedBy
    @Column(nullable = false,updatable = false)
    private Integer createdBy ;

    @LastModifiedBy
    @Column(insertable = false)
    private Integer lastModifiedBy;

}
