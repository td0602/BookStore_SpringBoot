package com.example.ebookapp.controller.admin;

import com.example.ebookapp.model.CustomUserDetails;
import com.example.ebookapp.model.User;
import com.example.ebookapp.service.StorageService;
import com.example.ebookapp.service.UserService;
import com.example.ebookapp.test.upload_file.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String editProfile(RedirectAttributes redirectAttributes,
                              @RequestParam(name = "oldPassword") String oldPassword,
                              @RequestParam(name = "newPassword", required = false) String newPassword,
                              @ModelAttribute("user") User user,
                              @RequestParam("fileImage")MultipartFile fileImage) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String myMessage = null;
        newPassword = newPassword.trim();

        if(passwordEncoder.matches(oldPassword, user.getPassword())) {
            String fileName = storageService.storeUserImage(fileImage);
            if(fileName != null && !fileName.equals("")) {
                user.setImage(fileName);
            }

            if(newPassword != null && !newPassword.equals("")) {
                user.setPassword(passwordEncoder.encode(newPassword));
            }
            if(userService.edit(user)) {
                myMessage = "Cập nhật thành công!";
                redirectAttributes.addFlashAttribute("successMessage", myMessage);
                return "redirect:/admin/home";
            } else {
                myMessage = "Cập nhật không thành công!";
                redirectAttributes.addFlashAttribute("errorMessage", myMessage);
                return "redirect:/admin/profile";
            }
        } else {
            myMessage = "Mật khẩu xác thực không đúng!";
            redirectAttributes.addFlashAttribute("errorMessage", myMessage);
            return "redirect:/admin/profile";
        }
    }
}
