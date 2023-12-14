package com.example.ebookapp.controller.admin;

import com.example.ebookapp.model.Category;
import com.example.ebookapp.model.CustomUserDetails;
import com.example.ebookapp.model.User;
import com.example.ebookapp.service.CategoryService;
import com.example.ebookapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class CommonAttributeController {
//    @Autowired
//    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    public void addCommonAtributes(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<Category> categoryList = categoryService.getAll();
        model.addAttribute("customUserDetails", customUserDetails);
        model.addAttribute("categoryList", categoryList);
    }

//    @ModelAttribute("/load-categorys")
//    public void loadCategory(Model model) {
//        List<Category> categoryList = categoryService.getAll();
//        model.addAttribute("categoryList", categoryList);
//    }
}
