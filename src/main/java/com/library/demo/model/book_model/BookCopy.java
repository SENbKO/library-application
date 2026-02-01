package com.library.demo.model.book_model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_copies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "isbn")
    private Book book;


    @Column(name = "copy_status")
    @Enumerated(EnumType.STRING)
    private BookCopyStatus copyStatus;

}
