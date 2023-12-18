package com.example.ebookapp.service;

import com.example.ebookapp.model.Cart;
import com.example.ebookapp.model.Order;
import com.example.ebookapp.model.User;
import com.example.ebookapp.repository.BookRepository;
import com.example.ebookapp.repository.CartRepository;
import com.example.ebookapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Boolean create(Order order, Long userId) {
        List<Cart> carts = cartRepository.findCartByUserId(userId);
        order.setCarts(carts);
        try {
            orderRepository.save(order);
            for (Cart cart: carts) {
                cart.setOrder(order);
                cart.setStatus(false);
                cartRepository.save(cart);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean createByOneBook(Order order, Long bookId, User user) {
        try {
            Cart cart = new Cart(bookRepository.findById(bookId).get());
            List<Cart> carts = new ArrayList<>();
            carts.add(cart);
            order.setCarts(carts);
            cart.setOrder(order);
            cart.setUser(user);
            cart.setStatus(false);
            orderRepository.save(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            orderRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
