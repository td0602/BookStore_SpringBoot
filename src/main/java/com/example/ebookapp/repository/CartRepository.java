package com.example.ebookapp.repository;

import com.example.ebookapp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findCartByUserId(Long userId);
}
