package com.example.ebookapp.test_filter_book;

import com.example.ebookapp.model.BookDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestBookRepository extends JpaRepository<BookDetails, Long> {
    Page<BookDetails> findAll(Specification<BookDetails> specification, Pageable pageable);
    Page<BookDetails> findAll(Pageable pageable);
}
