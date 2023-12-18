package com.example.ebookapp.service;

import com.example.ebookapp.model.BookDetails;
import org.springframework.data.domain.Page;

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
}
