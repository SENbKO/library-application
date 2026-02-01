package com.library.demo.repository;

import com.library.demo.model.book_model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {

}
