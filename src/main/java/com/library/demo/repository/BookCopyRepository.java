package com.library.demo.repository;

import com.library.demo.model.book_model.BookCopy;
import com.library.demo.model.book_model.BookCopyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    long countByBookIsbnAndCopyStatus(String isbn, BookCopyStatus status);

    List<BookCopy> findByBookIsbn(String isbn);

    List<BookCopy> findByBookIsbnAndCopyStatus(String isbn, BookCopyStatus status);

}
