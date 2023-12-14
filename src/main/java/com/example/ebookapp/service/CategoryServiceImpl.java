package com.example.ebookapp.service;

import com.example.ebookapp.model.Category;
import com.example.ebookapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Boolean create(Category category) {
        try {
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        // findById(id) tra ve Optional<Category> --> .get de lay ra object Category
        return categoryRepository.findById(id).get();
    }

    @Override
    public Boolean update(Category category) {
        try {
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            categoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Category> search(String keyword) {
        return categoryRepository.searchCategory(keyword);
    }

    //pageNo la so trang
    @Override
    public Page<Category> getAll(Integer pageNo) {
        // do chi so trong array chay tu: 0 1 2 ... --> pageNo-1
        Pageable pageable = PageRequest.of(pageNo-1, 2); // (currentPage, gioi han SL product trong 1 trang)
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> search(String keyword, Integer pageNo) {
        List list = search(keyword);
        Pageable pageable = PageRequest.of(pageNo-1,2);
        // tim duoc index bat dau de cat
        Integer start = (int) pageable.getOffset(); // start la vi tri (index trong list) bat dau cua ban ghi trong 1 trang
        Integer end = (int) ((pageable.getOffset()+pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset()+pageable.getPageSize());
        list = list.subList(start, end); // lay ra list con voi index start va end
        return new PageImpl<Category>(list, pageable, search(keyword).size());
    }
}
