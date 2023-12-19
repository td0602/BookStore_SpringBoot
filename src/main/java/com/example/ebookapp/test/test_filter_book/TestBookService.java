package com.example.ebookapp.test.test_filter_book;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;

public interface TestBookService {
    Page<BookDetails> getAll(Specification<BookDetails> specification, Integer pageNo);
    Page<BookDetails> getAll(Integer pageNo);
    Page<BookDetails> getBooksByFilter(
                                       @Param("category") Category category,
                                       @Param("orderBy") String orderBy,
                                       @Param("keyword") String keyword, Integer pageNo);
}
