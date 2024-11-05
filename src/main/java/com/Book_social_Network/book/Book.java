package com.Book_social_Network.book;

import com.Book_social_Network.common.BaseEntity;
import com.Book_social_Network.feedback.Feedback;
import com.Book_social_Network.history.BookTransactionHistory;
import com.Book_social_Network.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Book extends BaseEntity {

    private String title;
    private String author;
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean sharable;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner ;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks ;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories ;


    @Transient
    public double getRate(){
        if(feedbacks == null || feedbacks.isEmpty())
            return 0.0;

        var rate = this.feedbacks.stream()
                .mapToDouble(Feedback::getRate)
                .average()
                .orElse(0.0);

        return Math.round(rate *10.0)/10.0;
    }

}
