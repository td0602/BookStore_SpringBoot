package com.example.ebookapp.service;

import com.example.ebookapp.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Boolean create(Category category);
    List<Category> getAll();
    Category findById(Long id);

    Boolean update(Category category);
    Boolean delete(Long id);
    List<Category> search(String keyword);

    //Pagenation
    Page<Category> getAll(Integer pageNo); //pageNo la currentPage
    //Search + Pagenation
    Page<Category> search(String keyword, Integer pageNo);
}
