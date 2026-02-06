package com.library.demo.controller;

import com.library.demo.dto.BookRequest;
import com.library.demo.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final InventoryService inventoryService;

    public BookController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
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
}
