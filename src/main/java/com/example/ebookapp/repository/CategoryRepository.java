package com.example.ebookapp.repository;

import com.example.ebookapp.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    //custom lai de list sap xep theo ID tang dan
    @Query("SELECT c FROM Category AS c ORDER BY c.id ASC")
    List<Category> findAll();
    @Query("SELECT c FROM Category AS c ORDER BY c.id ASC")
    Page<Category> findAll(Pageable pageable);

    @Query("SELECT c FROM Category c WHERE c.categoryName LIKE %?1% ORDER BY c.id")
    List<Category> searchCategory(String keyword);


}
