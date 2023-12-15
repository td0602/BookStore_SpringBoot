package com.example.ebookapp.controller;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private BookService bookService;
    @GetMapping()
    public String home(Model model) {
        List<BookDetails> allBooks = bookService.getAll();
        List<BookDetails> newBooks = bookService.getAll();
        model.addAttribute("allBooks", allBooks);
        Collections.reverse(newBooks);
        model.addAttribute("newBooks", newBooks);
        return "index";
    }
}
