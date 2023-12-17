package com.example.ebookapp.controller.user;


import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Cart;
import com.example.ebookapp.model.CustomUserDetails;
import com.example.ebookapp.model.User;
import com.example.ebookapp.service.BookService;
import com.example.ebookapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private BookService bookService;

    @GetMapping("/add-cart/{bookId}")
    public String add(@PathVariable("bookId") Long bookId,
                      @AuthenticationPrincipal CustomUserDetails customUserDetails,
                      RedirectAttributes redirectAttributes) {
        String myMessage = null;
        BookDetails book = bookService.findById(bookId);
        Cart cart = new Cart(book);
        cart.setUser(customUserDetails.getUser());
        if(cartService.create(cart)) {
            myMessage = "Đã thêm sách vào giỏ hàng!";
            redirectAttributes.addFlashAttribute("successMessage", myMessage);
            return "redirect:/user/cart";
        }
        myMessage = "Thêm sách vào giỏ hàng thất bại";
        redirectAttributes.addFlashAttribute("errorMessage", myMessage);
        return "redirect:/home";
    }

    @GetMapping("/cart")
    public String cart(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<Cart> carts = cartService.getByUserId(customUserDetails.getUserId());
        model.addAttribute("carts", carts);
        return "user/cart";
    }

    @GetMapping("/delete-cart/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        String myMessage = null;
        if(cartService.delete(id)) {
            return "redirect:/user/cart";
        }
        myMessage = "Lỗi";
        redirectAttributes.addFlashAttribute("errorMessage", myMessage);
        return "redirect:/user/cart";
    }
}
