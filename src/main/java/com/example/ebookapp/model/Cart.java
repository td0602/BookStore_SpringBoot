package com.example.ebookapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bookName")
    private String bookName;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "image")
    private String image;
    @Column(name = "price")
    private Double price;
    @Column(name = "total")
    private Double total;
    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bookId", referencedColumnName = "id")
    private BookDetails book;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private Order order;

    public Cart(BookDetails book) {
        this.status = true;
        this.quantity = 1L;
        this.book = book;
        this.bookName = book.getBookName();
        this.price = book.getPrice();
        this.total = book.getPrice();;
        this.image = book.getImage();
    }
}
