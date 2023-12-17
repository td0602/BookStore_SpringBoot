package com.example.ebookapp.repository;

import com.example.ebookapp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.status=true ORDER BY c.id ASC")
    List<Cart> findCartByUserId(Long userId);
}
