package com.example.ebookapp.controller;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Category;
import com.example.ebookapp.service.BookService;
import com.example.ebookapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping()
public class HomeController {
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
}
