package com.example.ebookapp.test.test_filter_book;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TestBookRepository extends JpaRepository<BookDetails, Long> {
    Page<BookDetails> findAll(Specification<BookDetails> specification, Pageable pageable);
    Page<BookDetails> findAll(Pageable pageable);
    @Query("SELECT b FROM BookDetails b " +
            "WHERE (:category IS NULL OR b.category = :category) " +
            "AND (:keyword IS NULL OR b.bookName LIKE %:keyword%) " +
            "ORDER BY " +
            "CASE WHEN :orderBy = 'increasing' AND :orderBy != '' THEN b.price END ASC, " +
            "CASE WHEN :orderBy = 'decreasing' AND :orderBy != '' THEN b.price END DESC")
        //    Param mặc định có cả TH null
    Page<BookDetails> findBooksByFilter(@Param("category") Category category,
                                        @Param("orderBy") String orderBy,
                                        @Param("keyword") String keyword,
                                        Pageable pageable);
}
