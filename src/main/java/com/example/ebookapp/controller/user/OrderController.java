package com.example.ebookapp.controller.user;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Cart;
import com.example.ebookapp.model.CustomUserDetails;
import com.example.ebookapp.model.Order;
import com.example.ebookapp.service.BookService;
import com.example.ebookapp.service.CartService;
import com.example.ebookapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class OrderController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private BookService bookService;

    @GetMapping("/order")
    public String orderView(Model model,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails,
                            @RequestParam(value = "bookId", required = false) Long bookId) {
        Order order = new Order();
        List<Cart> carts = new ArrayList<>();
        if(bookId != null) {
            Cart cart = new Cart(bookService.findById(bookId));
            carts.add(cart);
            order.setOrderTotal(cart.getTotal());
            model.addAttribute("bookId", bookId);
        } else {
            carts = cartService.getByUserId(customUserDetails.getUserId());
            for(Cart cart: carts) {
                order.setOrderTotal(order.getOrderTotal()+ cart.getTotal());
            }
        }
        model.addAttribute("carts", carts);
        model.addAttribute("order", order);
        return "user/order";
    }

    @PostMapping("/order")
    public String order(@ModelAttribute("order") Order order,
                        @AuthenticationPrincipal CustomUserDetails customUserDetails,
                        RedirectAttributes redirectAttributes,
                        @RequestParam(value = "bookId", required = false) Long bookId) {
        String myMessage = null;
        if(bookId != null) {
            if(orderService.createByOneBook(order, bookId, customUserDetails.getUser())) {
                myMessage = "Tạo đơn hàng thành công!";
                redirectAttributes.addFlashAttribute("successMessage", myMessage);
                return "redirect:/home";
            }
        } else {
            if(orderService.create(order, customUserDetails.getUserId())) {
                myMessage = "Tạo đơn hàng thành công!";
                redirectAttributes.addFlashAttribute("successMessage", myMessage);
                return "redirect:/home";
            }
        }
        myMessage = "Tạo đơn hàng thất bại!";
        redirectAttributes.addFlashAttribute("errorMessage", myMessage);
        return "redirect:/user/order";
    }

    @GetMapping("/orders")
    public String orders(Model model,
                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<Order> orders = orderService.getByPhoneNumber(customUserDetails.getUser().getPhoneNumber());
        model.addAttribute("orders", orders);
        return "user/view-order";
    }

    @GetMapping("/delete-order/{id}")
    public String delete(RedirectAttributes redirectAttributes,
                         @PathVariable("id") Long id) {
        String myMessage = null;
        if(orderService.delete(id)) {
            myMessage = "Đã hủy đơn hàng!";
            redirectAttributes.addFlashAttribute("successMessage", myMessage);
            return "redirect:/user/orders";
        }
        myMessage = "Hủy đơn hàng không thành công!";
        redirectAttributes.addFlashAttribute("errorMessage", myMessage);
        return "redirect:/user/orders";
    }

    @GetMapping("/books-order/{id}")
    public String booksOrder(Model model,
                      @PathVariable("id") Long id) {
        Order order = orderService.findById(id);
        order.setOrderTotal(order.getOrderTotal()-70);
        List<Cart> carts = cartService.getByOrderId(id);
        model.addAttribute("order", order);
        model.addAttribute("carts", carts);
        return "user/order";
    }

}
