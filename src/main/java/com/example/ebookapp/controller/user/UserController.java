package com.example.ebookapp.controller.user;

import com.example.ebookapp.model.CustomUserDetails;
import com.example.ebookapp.model.User;
import com.example.ebookapp.model.UserRole;
import com.example.ebookapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String userHome() {
        return "redirect:/home";
    }

    @GetMapping("/account")
    public String accout(Model model,
                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = userService.findByUserName(customUserDetails.getUsername());
        model.addAttribute("user", user);
        return "user/account";
    }

    @PostMapping("/update-account")
    public String updateAccount(RedirectAttributes redirectAttributes,
                                @ModelAttribute("user") User user,
                                @RequestParam(name = "oldPassword") String oldPassword,
                                @RequestParam(name = "newPassword", required = false) String newPassword ) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String myMessage = null;
        newPassword = newPassword.trim();
        if(passwordEncoder.matches(oldPassword, user.getPassword())) {
            if(newPassword != null && !newPassword.equals("")) {
                user.setPassword(passwordEncoder.encode(newPassword));
            }
            if(userService.edit(user)) {
                myMessage = "Cập nhật thành công!";
                redirectAttributes.addFlashAttribute("successMessage", myMessage);
                return "redirect:/home";
            } else {
                myMessage = "Cập nhật không thành công!";
                redirectAttributes.addFlashAttribute("errorMessage", myMessage);
                return "redirect:/user/account";
            }
        } else {
            myMessage = "Mật khẩu xác thực không đúng!";
            redirectAttributes.addFlashAttribute("errorMessage", myMessage);
            return "redirect:/user/account";
        }
    }

}
