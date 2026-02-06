package com.library.demo.service;

import com.library.demo.dto.BookRequest;
import com.library.demo.model.book_model.Book;
import com.library.demo.model.book_model.BookCopy;
import com.library.demo.model.book_model.BookCopyStatus;
import com.library.demo.repository.BookCopyRepository;
import com.library.demo.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {
    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;

    public InventoryService(BookCopyRepository bookCopyRepository, BookRepository bookRepository) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookRepository = bookRepository;
    }

    public BookCopy borrowCopy(Long copyId){
        BookCopy bookCopy = bookCopyRepository.findById(copyId)
                .orElseThrow(() -> new RuntimeException("Copy not found"));
        if(bookCopy.getCopyStatus()!= BookCopyStatus.AVAILABLE){
            throw new IllegalStateException("Copy is not available");
        }

        bookCopy.setCopyStatus(BookCopyStatus.BORROWED);
        return bookCopy;
    }


    public void addBook(BookRequest bookRequest) {
        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .isbn(bookRequest.getIsbn())
                .author(bookRequest.getAuthor())
                .publishedYear(bookRequest.getPublishedYear())
                .build();

        List<BookCopy> bookCopies = new ArrayList<>();
        for(int i =0; i < bookRequest.getNumberOfCopies(); i++){
            BookCopy bookCopy = BookCopy.builder()
                    .book(book)
                    .copyStatus(BookCopyStatus.AVAILABLE)
                    .build();
            bookCopies.add(bookCopy);
        }

        book.setBookCopies(bookCopies);
        bookRepository.save(book);

    }
}
