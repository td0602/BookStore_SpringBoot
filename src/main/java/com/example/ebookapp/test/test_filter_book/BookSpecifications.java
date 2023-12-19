package com.example.ebookapp.test.test_filter_book;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Category;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {
    public static Specification<BookDetails> filterByCategory(Category category) {
        return (Root<BookDetails> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<BookDetails> filterByKeyword(String keyword) {
        return (Root<BookDetails> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("bookName")), "%" + keyword.toLowerCase() + "%");
    }

    public static Specification<BookDetails> orderBy(String orderBy) {
        return (Root<BookDetails> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if ("increasing".equalsIgnoreCase(orderBy)) {
                query.orderBy(criteriaBuilder.asc(root.get("price")));
            } else if ("decreasing".equalsIgnoreCase(orderBy)) {
                query.orderBy(criteriaBuilder.desc(root.get("price")));
            }
            return null;
        };
    }
    public static Specification<BookDetails> buildBookSpecificationByCategory(Category category) {
        return Specification.where(filterByCategory(category));
    }
    public static Specification<BookDetails> buildBookSpecificationByKeyword(String keyword) {
        return Specification.where(filterByKeyword(keyword));
    }
    public static Specification<BookDetails> buildBookSpecificationByOrderBy(String orderBy) {
        return Specification.where(orderBy(orderBy));
    }
    public static Specification<BookDetails> buildBookSpecificationByCategoryAndKeyword(Category category,String keyword) {
        return Specification.where(filterByCategory(category))
                .and(filterByKeyword(keyword));
    }
    public static Specification<BookDetails> buildBookSpecificationByOrderByAndKeyword(String orderBy,String keyword) {
        return Specification.where(orderBy(orderBy))
                .and(filterByKeyword(keyword));
    }
    public static Specification<BookDetails> buildBookSpecificationByCategoryAndOrderBy(Category category,String orderBy) {
        return Specification.where(filterByCategory(category))
                .and(orderBy(orderBy));
    }
    public static Specification<BookDetails> buildBookpecification(Category category, String keyword, String orderBy) {
        return Specification.where(filterByCategory(category))
                .and(filterByKeyword(keyword))
                .and(orderBy(orderBy));
    }
}
