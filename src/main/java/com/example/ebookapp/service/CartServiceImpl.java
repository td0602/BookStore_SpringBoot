package com.example.ebookapp.service;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Cart;
import com.example.ebookapp.model.User;
import com.example.ebookapp.repository.BookRepository;
import com.example.ebookapp.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Cart> getByUserId(Long userId) {
        return cartRepository.findCartByUserId(userId);
    }

    @Override
    public List<Cart> getByOrderId(Long orderId) {
        return cartRepository.findAllByOrderId(orderId);
    }


    @Override
    public Cart findById(Long id) {
        return cartRepository.findById(id).get();
    }

    @Override
    public Boolean create(Long bookId, Long quantity, User user) {
        //lay tat ca cart da co trong gio hang cua user
        try {
            List<Cart> carts = cartRepository.findCartByUserId(user.getId());
            for(Cart cart: carts) {
                if(cart.getBook().getId()==bookId) {
                    if(quantity != null) {
                        cart.setQuantity(cart.getQuantity()+quantity);
                    } else {
                        cart.setQuantity(cart.getQuantity()+1);
                    }
                    cart.setTotal(cart.getPrice()*cart.getQuantity());
                    cartRepository.save(cart);
                    return true;
                }
            }

            //TH trong gio hang chua co sach nay
            BookDetails book = bookRepository.findById(bookId).get();
            Cart cart = new Cart(book);
            if(quantity!=null) {
                cart.setQuantity(quantity);
            }
            cart.setTotal(cart.getQuantity()*cart.getPrice());
            cart.setUser(user);
            cartRepository.save(cart);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            cartRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean update(Cart cart) {
        try {
            cartRepository.save(cart);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
