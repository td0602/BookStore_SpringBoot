package com.example.ebookapp.service;

import com.example.ebookapp.model.Order;
import com.example.ebookapp.model.User;

import java.util.List;

public interface OrderService {
    List<Order> getByPhoneNumber(String phoneNumber);
    Boolean create(Order order, Long userId);
    Boolean createByOneBook(Order order, Long bookId, User user);
    Boolean delete(Long id);
}
