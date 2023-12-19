package com.example.ebookapp.repository;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Category;
import com.example.ebookapp.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByPhoneNumber(String phoneNumber);

    @Query("SELECT b FROM Order b " +
            "WHERE (:keyword IS NULL OR b.orderId LIKE %:keyword%) " +
            "OR (:keyword IS NULL OR b.phoneNumber LIKE %:keyword%)" +
            "OR (:keyword IS NULL OR b.fullName LIKE %:keyword%)" +
            "OR (:keyword IS NULL OR b.email LIKE %:keyword%)")
        //    Param mặc định có cả TH null
    Page<Order> findOrdersByFilter(@Param("keyword") String keyword,
                                        Pageable pageable);
}
