package com.example.ebookapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orderId")
    private String orderId;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "adddress")
    private String address;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "orderTotal")
    private Double orderTotal;
    @Column(name = "payment")
    private String payment;
    @Column(name = "note")
    private String note;
    @Column(name = "date")
    private Date date;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL})
    private List<Cart> carts;

    public Order() {
        this.date = new Date();
        this.orderTotal = 0.0;
        this.orderId = "TD-ORDER-"+this.date.toString();
    }
}
