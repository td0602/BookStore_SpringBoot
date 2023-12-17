package com.example.ebookapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book_details")
public class BookDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bookname", nullable = false)
    private String bookName;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "author")
    private String author;
    @Column(name = "image")
    private String image;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "book")
    private Set<Cart> carts;
}
