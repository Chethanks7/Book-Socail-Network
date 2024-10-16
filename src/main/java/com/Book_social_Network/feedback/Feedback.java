package com.Book_social_Network.feedback;

import com.Book_social_Network.book.Book;
import com.Book_social_Network.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Feedback extends BaseEntity {

    private Double rate;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book ;


}
