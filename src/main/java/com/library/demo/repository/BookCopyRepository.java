package com.library.demo.repository;

import com.library.demo.model.book_model.BookCopy;
import com.library.demo.model.book_model.BookCopyStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    long countByBookIsbnAndCopyStatus(String isbn, BookCopyStatus status);

    List<BookCopy> findByBookIsbn(String isbn);

    List<BookCopy> findByBookIsbnAndCopyStatus(String isbn, BookCopyStatus status);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT  bc
            FROM BookCopy bc
            WHERE bc.book.isbn = :isbn
            AND   bc.copyStatus = 'AVAILABLE'
            ORDER BY bc.id
            LIMIT 1
            """)
    Optional<BookCopy> findFirstAvailableBookForUpdate(@Param("isbn") String isbn);

}
