package com.Book_social_Network.history;

import com.Book_social_Network.book.Book;
import com.Book_social_Network.common.BaseEntity;
import com.Book_social_Network.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BookTransactionHistory extends BaseEntity {

    private boolean returned ;
    private boolean returnApproved;

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book ;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user ;

}
