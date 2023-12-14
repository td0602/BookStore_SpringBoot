package com.example.ebookapp.controller.admin;

import com.example.ebookapp.model.CustomUserDetails;
import com.example.ebookapp.model.User;
import com.example.ebookapp.service.StorageService;
import com.example.ebookapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private StorageService storageService;

    @GetMapping("/admin/home")
    public String index() {
        return "admin/index";
    }
    @GetMapping("/admin/profile")

    public String profile(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // Lam nhu thế này là fix lỗi User trong database đã cập nhật nhưng thông tin của customUserDetails của phiên đăng nhập này chưa cập nhật
        // Nên ta cần lấy thông tin mới nhất từ database
        User user = userService.findByUserName(customUserDetails.getUsername());
        model.addAttribute("user", user);
        return "admin/profile";
    }

    @PostMapping("/admin/edit-profile")
    public String editProfile(@ModelAttribute("user") User user, @RequestParam("fileImage")MultipartFile fileImage) {
        storageService.storeUserImage(fileImage);
        String fileName = fileImage.getOriginalFilename();
        if(fileName != null) {
            if(!fileName.equals("")) {
                user.setImage(fileName);
            }
        }
        if(userService.edit(user)) {
            return "redirect:/admin/profile";
        }
        return "redirect:/admin/profile";
    }
}
