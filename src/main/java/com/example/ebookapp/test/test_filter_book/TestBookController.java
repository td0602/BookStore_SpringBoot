package com.example.ebookapp.test.test_filter_book;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Category;
import com.example.ebookapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestBookController {
    @Autowired
    private TestBookService testBookService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/test_filter_book")
    public String index(Model model,
                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                        @RequestParam(name = "keyword", required = false) String keyword,
                        @RequestParam(name = "categoryId", required = false) Long categoryId,
                        @RequestParam(name = "orderBy", required = false) String orderBy) {
        /* CACH 1:
        Specification<BookDetails> specification = null;
        Category category = null;
        if(categoryId != null) {
            category = categoryService.findById(categoryId);
        }
        if(keyword != null && !keyword.equals("")) {
            if(categoryId != null) {
                if(orderBy != null && !orderBy.equals("")) {
                    specification = BookSpecifications.buildBookpecification(category, keyword, orderBy);
                } else {
                    specification = BookSpecifications.buildBookSpecificationByCategoryAndKeyword(category, keyword);
                }
            } else {
                if(orderBy != null && !orderBy.equals("")) {
                    specification = BookSpecifications.buildBookSpecificationByOrderByAndKeyword(orderBy, keyword);
                } else {
                    specification = BookSpecifications.buildBookSpecificationByKeyword(keyword);
                }
            }
        } else {
            if(categoryId != null) {
                if(orderBy != null && !orderBy.equals("")) {
                    specification = BookSpecifications.buildBookSpecificationByCategoryAndOrderBy(category, orderBy);
                } else {
                    specification = BookSpecifications.buildBookSpecificationByCategory(category);
                }
            } else {
                if(orderBy != null&& !orderBy.equals("")) {
                    specification = BookSpecifications.buildBookSpecificationByOrderBy(orderBy);
                }
            }
        }
        Page<BookDetails> list = null;
        if(specification != null) {
            list = testBookService.getAll(specification, pageNo);
        } else {
            list = testBookService.getAll(pageNo);
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("categoryId", categoryId);

        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("bookList", list);
        return "test/test_filterbook/test";
         */

        //Cach 2
        Category category = null;
        if(categoryId != null) {
            category = categoryService.findById(categoryId);
        }
        Page<BookDetails> list = testBookService.getBooksByFilter(category, orderBy, keyword, pageNo);
        model.addAttribute("keyword", keyword);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("categoryId", categoryId);

        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("bookList", list);
        return "test/test_filter_book/test";
    }
}
