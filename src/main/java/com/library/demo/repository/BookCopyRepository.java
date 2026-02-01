package com.library.demo.repository;

import com.library.demo.model.book_model.BookCopy;
import com.library.demo.model.book_model.BookCopyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    long countByBookIsbnAndStatus(String isbn, BookCopyStatus status);

    List<BookCopy> findByBookIsbn(String isbn);

    List<BookCopy> findByBookIsbnAndStatus(String isbn, BookCopyStatus status);
}
