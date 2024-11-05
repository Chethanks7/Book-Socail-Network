package com.Book_social_Network.book;

import com.Book_social_Network.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    public Book toBook(BookRequest bookRequest) {

        return Book.builder()
                .id(bookRequest.id())
                .author(bookRequest.authorName())
                .isbn(bookRequest.isbn())
                .synopsis(bookRequest.synopsis())
                .archived(false)
                .sharable(bookRequest.sharable())
                .build();
    }

    public BookResponse toBookResponse(Book book) {

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthor())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .rate(book.getRate())
                .archived(book.isArchived())
                .sharable(book.isSharable())
                .owner(book.getOwner().fullName())
                //.cover()
                .build();
    }

    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history) {
        return  BorrowedBookResponse.builder()
                .id(history.getBook().getId())
                .authorName(history.getBook().getAuthor())
                .isbn(history.getBook().getIsbn())
                .title(history.getBook().getTitle())
                .rate(history.getBook().getRate())
                .returned(history.isReturned())
                .returnApproved(history.isReturnApproved())
                .build();
    }
}
