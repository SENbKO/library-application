package com.library.demo.model.book_model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    private String isbn;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    private Integer publishedYear;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookCopy> bookCopies = new ArrayList<>();

}
