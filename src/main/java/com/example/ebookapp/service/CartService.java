package com.example.ebookapp.service;

import com.example.ebookapp.model.Cart;

import java.util.List;
import java.util.Set;

public interface CartService {
    List<Cart> getByUserId(Long userId);
    Cart findById(Long id);
    Boolean create(Cart cart);
    Boolean delete(Long id);
    Boolean update(Cart cart);
}
