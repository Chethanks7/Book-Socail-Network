package com.Book_social_Network.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Integer> {

    @Query("""
    SELECT history
    From BookTransactionHistory history
    WHERE history.user.id = :id
    """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Integer id, Pageable pageable);
}
