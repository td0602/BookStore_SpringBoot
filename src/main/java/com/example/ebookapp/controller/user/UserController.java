package com.example.ebookapp.controller.user;

import com.example.ebookapp.model.User;
import com.example.ebookapp.model.UserRole;
import com.example.ebookapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/logon")
    public String logon() {
        return "logon";
    }

    @GetMapping("/register")
    public String registerView(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute("user") User user) {
        String myMessage = null;
        if(userService.create(user)) {
            myMessage = "Đăng ký thành công!";
            model.addAttribute("successMessage", myMessage);
            return "index";
        }
        myMessage = "Đăng ký thất bại! Có thể thông tin bị trùng!";
        model.addAttribute("messages", myMessage);
        return "register";
    }

    @GetMapping("/user/home")
    public String userHome() {
        return "redirect:/home";
    }
}
