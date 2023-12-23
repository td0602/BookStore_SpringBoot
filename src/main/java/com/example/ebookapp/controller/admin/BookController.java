package com.example.ebookapp.controller.admin;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Category;
import com.example.ebookapp.model.CustomUserDetails;
import com.example.ebookapp.service.BookService;
import com.example.ebookapp.service.CategoryService;
import com.example.ebookapp.service.StorageService;
import com.example.ebookapp.test.upload_file.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BookController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BookService bookService;
    @Autowired
    private StorageService storageService;
    @GetMapping("/books")
    public String index(Model model,
                        @RequestParam(name = "keyword", required = false) String keyword,
                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                        @RequestParam(name = "categoryId", required = false) Long categoryId,
                        @RequestParam(value = "orderBy", required = false) String orderBy) {
        //Lam lai
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
        model.addAttribute("list", list);
        return "admin/book/index";
    }

    @GetMapping("/add-book")
    public String viewAdd(Model model) {
        List<Category> categoryList = categoryService.getAll();
        BookDetails bookDetails = new BookDetails();
        bookDetails.setStatus(true);
//        model.addAttribute("customUserDetails", customUserDetails);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("bookDetails", bookDetails);
        return "admin/book/add";
    }

    @PostMapping("/add-book")
    // @RequestParam("fileImage") lay theo name file image trong html
    public String add(Model model, @ModelAttribute("bookDetails") BookDetails bookDetails,
                      @RequestParam("fileImage") MultipartFile fileImage) {
        String myMessage = null;
        bookDetails.setImage(storageService.storeBookImage(fileImage));
        if(bookService.create(bookDetails)) {
            myMessage = "Thêm mới thành công!";
            model.addAttribute("successMessage", myMessage);
            model.addAttribute("bookDetails", new BookDetails());
            return "admin/book/add";
        } else {
            myMessage = "Thêm mới thất bại!";
            model.addAttribute("errorMessage", myMessage);
        }
        return "admin/book/add";
    }

    @GetMapping("/edit-book/{id}")
    public String viewEdit(Model model, @PathVariable("id") Long id) {
        BookDetails bookDetails = bookService.findById(id);
        model.addAttribute("bookDetails", bookDetails);
        return "admin/book/edit";
    }

    @PostMapping("/edit-book/{id}")
    public String edit(Model model,
                       @ModelAttribute("bookDetails") BookDetails bookDetails,
                       @RequestParam("fileImage") MultipartFile fileImage) {
        String myMessage = null;
        try {
            String fileName = storageService.storeBookImage(fileImage);
            if(fileName != null && !fileName.equals("")) {
                bookDetails.setImage(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(bookService.update(bookDetails)) {
            myMessage = "Cập nhật thành công!";
            model.addAttribute("successMessage", myMessage);
            return "admin/index";
        }
        myMessage = "Cập nhật thất bại!";
        model.addAttribute("errorMessage", myMessage);
        return "redirect:/admin/edit-book/"+ bookDetails.getId();
    }

    @GetMapping("/delete-book/{id}")
    public String delete(@PathVariable("id") Long id) {
        bookService.delete(id);
        return "redirect:/admin/books";
    }
}
