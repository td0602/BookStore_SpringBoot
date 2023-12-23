package com.example.ebookapp.controller.user;


import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Cart;
import com.example.ebookapp.model.CustomUserDetails;
import com.example.ebookapp.model.User;
import com.example.ebookapp.service.BookService;
import com.example.ebookapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/add-cart")
    public String add(@RequestParam("bookId") Long bookId,
                      @RequestParam(value = "quantity", required = false) Long quantity,
                      @AuthenticationPrincipal CustomUserDetails customUserDetails,
                      RedirectAttributes redirectAttributes) {
        String myMessage = null;
        if(cartService.create(bookId, quantity, customUserDetails.getUser())) {
            myMessage = "Đã thêm sách vào giỏ hàng!";
            redirectAttributes.addFlashAttribute("successMessage", myMessage);
            return "redirect:/grid-shop";
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

    @GetMapping("/update-cart")
    public String update(@ModelAttribute("carts") List<Cart> carts,
                         RedirectAttributes redirectAttributes) {
        String myMessage = null;
        Boolean state = true;
        for(Cart cart: carts) {
            if (!cartService.update(cart)) {
                state = false;
            }
        }
        if(state) {
            myMessage = "Cập nhật giỏ hàng thành công!";
            redirectAttributes.addFlashAttribute("successMessage", myMessage);
        } else {
            myMessage = "Cập nhật giỏ hàng thất bại!";
            redirectAttributes.addFlashAttribute("errorMessage", myMessage);
        }
        return "redirect:/user/cart";
    }

    //update cart khi thay đổi quantity của giỏ hàng
    @PostMapping("/update-cart")
    @ResponseBody
    public ResponseEntity<String> updateByCartId(@RequestParam("cartId") Long cartId,
                                         @RequestParam("quantity") Long quantity) {
        //tạo response để trả về cho Client
        ResponseEntity<String> response = null;
        Cart cart = cartService.findById(cartId);
        cart.setTotal(quantity*cart.getPrice());
        cart.setQuantity(quantity);
        if(cartService.update(cart)) {
            response = ResponseEntity.status(HttpStatus.CREATED)
                    .body("Update book quantity succsessfully!");
        } else {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR update book quantity");
        }
        return response;
    }
}
