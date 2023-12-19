package com.example.ebookapp.repository;

import com.example.ebookapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByPhoneNumber(String phoneNumber);
}
