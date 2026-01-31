package com.library.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @GetMapping("/health")
    public ResponseEntity<?> check(){
        return ResponseEntity.ok("Health ok");
    }
}
