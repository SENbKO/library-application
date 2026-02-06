package com.library.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String isbn;
    private String title;
    private String author;
    private Integer publishedYear;
    private Integer numberOfCopies;

}
