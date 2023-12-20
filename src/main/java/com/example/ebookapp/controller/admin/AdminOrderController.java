package com.example.ebookapp.controller.admin;

import com.example.ebookapp.model.Cart;
import com.example.ebookapp.model.Order;
import com.example.ebookapp.service.CartService;
import com.example.ebookapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @GetMapping("/orders")
    public String orders(Model model,
                         @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                         @RequestParam(value = "keyword", required = false) String keyword) {
        Page<Order> orders = orderService.getAll(keyword, pageNo);

        model.addAttribute("keyword", keyword);

        model.addAttribute("totalPage", orders.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("orders", orders);
        return "admin/order/order";
    }

    @GetMapping("/delete-order/{id}")
    public String delete(@PathVariable("id") Long id) {
        orderService.delete(id);
        return "redirect:/admin/orders";
    }

    @GetMapping("/order-details/{id}")
    public String orderDetails(Model model,
                               @PathVariable("id") Long id) {
        Order order = orderService.findById(id);
        List<Cart> carts = cartService.getByOrderId(id);
        model.addAttribute("order", order);
        model.addAttribute("carts", carts);
        return "admin/order/order-details";
    }
}
