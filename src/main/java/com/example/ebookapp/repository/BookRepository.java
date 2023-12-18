package com.example.ebookapp.repository;

import com.example.ebookapp.model.BookDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookDetails, Long> {
    // Custom lai de list sap xep thep ID tang dan
    @Query("SELECT b FROM BookDetails AS b ORDER BY b.id ASC")
    List<BookDetails> findAll();
    @Query("SELECT b FROM BookDetails AS b ORDER BY b.id ASC")
    Page<BookDetails> findAll(Pageable pageable);
    Page<BookDetails> findAllByOrderByPriceDesc(Pageable pageable);
    Page<BookDetails> findAllByOrderByPriceAsc(Pageable pageable);

    @Query("SELECT b FROM BookDetails b WHERE b.bookName LIKE %?1% ORDER BY b.id")// ?1 la co 1 tham so truyen vao
    List<BookDetails> searchBook(String keyword);

    List<BookDetails> findBookDetailsByCategoryId(Long id);
}
