package com.library.demo.controller;

import com.library.demo.dto.BookPageResponse;
import com.library.demo.dto.BookRequest;
import com.library.demo.model.user_model.User;
import com.library.demo.service.InventoryService;
import com.library.demo.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    private final InventoryService inventoryService;
    private final LoanService loanService;

    public BookController(InventoryService inventoryService, LoanService loanService) {
        this.inventoryService = inventoryService;
        this.loanService = loanService;
    }

    @GetMapping("/health")
    public ResponseEntity<?> check(){
        return ResponseEntity.ok("Health ok");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add_book")
    public ResponseEntity<?> addBook(@RequestBody BookRequest bookRequest){
        inventoryService.addBook(bookRequest);
        return ResponseEntity.ok().body(bookRequest.getTitle() + " added!");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/borrow/{isbn}")
    public ResponseEntity<?> loanBook(@PathVariable("isbn") String isbn, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        if(user != null){
            loanService.borrowBook(user, isbn);
            return ResponseEntity.ok("Book " + isbn + " added!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No such user");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/return/{loanId}")
    public ResponseEntity<?> returnBook(@PathVariable("loanId") long loanId, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        if(user != null){
            loanService.returnBook(user.getId(), loanId);
            return ResponseEntity.ok("Loan " + loanId + " returned!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No such user");
    }

    @GetMapping("/books")
    public ResponseEntity<?> showBooks(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "size", defaultValue = "5") Integer size
                                       ){

        BookPageResponse response = inventoryService.showBooks(page, size);
        return ResponseEntity.ok(response);
    }
}
