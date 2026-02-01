package com.library.demo.service;

import com.library.demo.model.book_model.BookCopy;
import com.library.demo.model.book_model.BookCopyStatus;
import com.library.demo.repository.BookCopyRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final BookCopyRepository bookCopyRepository;

    public InventoryService(BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
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


}
