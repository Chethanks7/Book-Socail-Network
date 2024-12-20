package com.Book_social_Network.book;

import com.Book_social_Network.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service ;

    @PostMapping("/save-book")
    public ResponseEntity<Integer> saveBook(
            @Valid @RequestBody BookRequest bookRequest,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.save(bookRequest,connectedUser));
    }

    @GetMapping({"book-id"})
    public ResponseEntity<BookResponse> findBookById(
          @PathVariable("book-id") Integer bookId
    ){
        return ResponseEntity.ok(service.findById(bookId));
    }

    @GetMapping("all-books")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0",required = false)int page,
            @RequestParam(name = "size", defaultValue = "10",required = false)int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok( service.findAllBooks(page,size,connectedUser));
    }

    @GetMapping("owner-books")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0",required = false)int page,
            @RequestParam(name = "size", defaultValue = "10",required = false)int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllBooksByOwner(page,size,connectedUser));
    }

    @GetMapping("owner-books")
    public ResponseEntity<PageResponse<BookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0",required = false)int page,
            @RequestParam(name = "size", defaultValue = "10",required = false)int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllBorrowedBooks(page,size,connectedUser));
    }


}
