package com.example.ebookapp.service;

import com.example.ebookapp.model.Order;
import com.example.ebookapp.model.User;

public interface OrderService {
    Boolean create(Order order, Long userId);
    Boolean createByOneBook(Order order, Long bookId, User user);
    Boolean delete(Long id);
}
