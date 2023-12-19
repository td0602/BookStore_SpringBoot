package com.example.ebookapp.service;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookService {
    Boolean create(BookDetails bookDetails);
    List<BookDetails> getAll();
    List<BookDetails> findByCategoryId(Long id);
    BookDetails findById(Long id);

    Boolean update(BookDetails bookDetails);
    Boolean delete(Long id);

    List<BookDetails> search(String keyword);

    //Pagenation and search + pagenation
    Page<BookDetails> getAll(Integer pageNo);
    Page<BookDetails> getByCategory(Long id, Integer pageNo);
    Page<BookDetails> search(String keyword, Integer pageNo);
    Page<BookDetails> getAllBookOrderByAsc(Integer pageNo);
    Page<BookDetails> getAllBookOrderByDesc(Integer PageNo);

    //Thay the 5 function tren
    Page<BookDetails> getBooksByFilter(
            @Param("category") Category category,
            @Param("orderBy") String orderBy,
            @Param("keyword") String keyword, Integer pageNo);
}
