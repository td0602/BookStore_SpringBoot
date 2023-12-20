package com.example.ebookapp.controller;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Category;
import com.example.ebookapp.model.User;
import com.example.ebookapp.service.BookService;
import com.example.ebookapp.service.CategoryService;
import com.example.ebookapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping()
public class CommonController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/home")
    public String home(Model model) {
        List<BookDetails> allBooks = bookService.getAll();
        List<BookDetails> newBooks = bookService.getAll();
        model.addAttribute("allBooks", allBooks);
        Collections.reverse(newBooks);
        model.addAttribute("newBooks", newBooks);
        return "index";
    }

    @GetMapping("/logon")
    public String logon() {
        return "login";
    }
    @GetMapping("/register")
    public String registerView(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "create-account";
    }
    @PostMapping("/register")
    public String register(RedirectAttributes redirectAttributes,
                           @ModelAttribute("user") User user) {
        String myMessage = null;
        if(userService.create(user)) {
            myMessage = "Đăng ký thành công!";
            redirectAttributes.addFlashAttribute("successMessage", myMessage);
            return "redirect:/home";
        }
        myMessage = "Đăng ký thất bại! Có thể thông tin bị trùng!";
        redirectAttributes.addFlashAttribute("errorMessage", myMessage);
        return "redirect:/register";
    }

    @GetMapping("/grid-shop")
    public String gridShop(Model model,
                           @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           @RequestParam(value = "categoryId", required = false) Long categoryId,
                           @RequestParam(value = "orderBy", required = false) String orderBy) {
        Category category = null;
        if(categoryId != null) {
            category = categoryService.findById(categoryId);
        }
        Page<BookDetails> list = bookService.getBooksByFilter(category, orderBy, keyword, pageNo);
        model.addAttribute("keyword", keyword);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("categoryId", categoryId);

        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("bookList", list);
        return "grid-shop";
    }
    @GetMapping("/single-book/{id}")
    public String singleBoook(Model model, @PathVariable("id") Long id) {
        BookDetails book = bookService.findById(id);
        model.addAttribute("book", book);
        return "/single-book";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
