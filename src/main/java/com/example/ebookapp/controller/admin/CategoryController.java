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
    public String add(Model model, @ModelAttribute("category") Category category) {
        String myMessage = null;
        if(categoryService.create(category)) {
            myMessage = "Thêm mới thành công!";
            model.addAttribute("successMessage", myMessage);
            Category newCategory = new Category();
            newCategory.setCategoryStatus(true);
            model.addAttribute("category", newCategory);
            return "admin/category/add";
        }
        myMessage = "Thêm mới thất bại";
        model.addAttribute("errorMessage", myMessage);
        return "admin/category/add";
    }

    @GetMapping("/edit-category/{id}")
    public String viewEdit(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/edit";
    }

    @PostMapping("/edit-category")
    public String edit(Model model, @ModelAttribute("category") Category category) {
        String myMessage = null;
        if(categoryService.update(category)) {
            myMessage = "Cập nhật thành công!";
            model.addAttribute("successMessage", myMessage);
            return "admin/category/edit";
        }
        myMessage = "Cập nhật thất bại!";
        return "admin/category/edit";
    }

    @GetMapping("/delete-category/{id}")
    public String delete(Model model, @PathVariable("id") Long id) {
        String myMessage = null;
        if(categoryService.delete(id)) {
            return "redirect:/admin/category";
        }
        myMessage = "Xóa thất bại!";
        model.addAttribute("errorMessage", myMessage);
        return "admin/index";
    }
}
