package com.Book_social_Network.book;

import com.Book_social_Network.common.PageResponse;
import com.Book_social_Network.history.BookTransactionHistory;
import com.Book_social_Network.history.BookTransactionHistoryRepository;
import com.Book_social_Network.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.Book_social_Network.book.BookSpecification.withOwnerId;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookTransactionHistoryRepository historyRepository ;
    private final BookMapper bookMapper;

    public Integer save(BookRequest bookRequest, Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();
        Book book = bookMapper.toBook(bookRequest);
        book.setOwner(user);
        return repository.save(book).getId();
    }


    public BookResponse findById(Integer bookId) {

        return repository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow( () ->
                        new EntityNotFoundException("book not found with id :"+bookId)
                );
    }

    public PageResponse<BookResponse> findAllBooks(
            int page, int size, Authentication connectedUser
    ) {

        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Book> books = repository.findAllDisplayableBooks(pageable,user.getId());
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
    return new PageResponse<>(
            bookResponses,
            books.getNumber(),
            books.getSize(),
            books.getTotalElements(),
            books.getTotalPages(),
            books.isFirst(),
            books.isLast()
    );
    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Book> books = repository.findAll(withOwnerId(user.getId()),pageable);
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> books = historyRepository.findAllBorrowedBooks(user.getId(),pageable);
        List<BorrowedBookResponse> bookResponses = books.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return  null;

    }
}
