package com.example.ebookapp.test_filter_book;

import com.example.ebookapp.model.BookDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

public interface TestBookService {
    Page<BookDetails> getAll(Specification<BookDetails> specification, Integer pageNo);
    Page<BookDetails> getAll(Integer pageNo);
}
