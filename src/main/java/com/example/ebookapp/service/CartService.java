package com.example.ebookapp.service;

import com.example.ebookapp.model.Cart;
import com.example.ebookapp.model.User;

import java.util.List;
import java.util.Set;

public interface CartService {
    List<Cart> getByUserId(Long userId);
    List<Cart> getByOrderId(Long orderId);
    Cart findById(Long id);
    Boolean create(Long bookId, Long quantity, User user);
    Boolean delete(Long id);
    Boolean update(Cart cart);
}
