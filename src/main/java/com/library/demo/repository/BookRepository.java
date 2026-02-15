package com.library.demo.repository;

import com.library.demo.dto.BookDto;
import com.library.demo.model.book_model.Book;
import com.library.demo.model.book_model.BookCopyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    @Query("""
            SELECT b.isbn as isbn, b.title as title, b.author as author, b.publishedYear as publishedYear FROM Book b
            WHERE EXISTS (
                SELECT bc FROM BookCopy bc
                WHERE bc.book = b
                AND bc.copyStatus = :status
            )
            """)
    Page<BookDto> findBooksByAvailableCopies(@Param("status") BookCopyStatus bookCopyStatus, Pageable pageable);
}
