package com.example.ebookapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book_orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookOrder {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quanity")
    private Long quanity;
    @Column(name = "price")
    private Double price;
    @Column(name = "bookName")
    private String bookName;
    @Column(name = "author")
    private String author;

    @ManyToOne
    @JoinColumn(name = "bookId", referencedColumnName = "id")
    private BookDetails book;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private Order order;
}
