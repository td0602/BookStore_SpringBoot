package com.example.ebookapp.controller.admin;

import com.example.ebookapp.model.Category;
import com.example.ebookapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    //pageNo: index page hien tai
    public String index(Model model, @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<Category> list = categoryService.getAll(pageNo);
        if(keyword != null) {
            keyword = keyword.trim();
            if(!keyword.equals("")) {
                list = categoryService.search(keyword,pageNo);
                model.addAttribute("keyword", keyword);
            }
        }
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("list", list);
        return "admin/category/index";
    }

    @GetMapping("/add-category")
    public String viewAdd(Model model) {
        Category category = new Category();
        category.setCategoryStatus(true);
        model.addAttribute("category", category);
        return "admin/category/add";
    }

    @PostMapping("/add-category")
    public String add(@ModelAttribute("category") Category category) {
        if(categoryService.create(category)) {
            return "redirect:/admin/category";
        }
        return "admin/category/add";
    }

    @GetMapping("/edit-category/{id}")
    public String viewEdit(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/edit";
    }

    @PostMapping("/edit-category")
    public String edit(@ModelAttribute("category") Category category) {
        if(categoryService.update(category)) {
            return "redirect:/admin/category";
        }
        return "admin/category/edit";
    }

    @GetMapping("/delete-category/{id}")
    public String delete(@PathVariable("id") Long id) {
        if(categoryService.delete(id)) {
            return "redirect:/admin/category";
        }
        return "admin/category/index";
    }
}
