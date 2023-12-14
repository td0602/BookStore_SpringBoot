package com.example.ebookapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "categoryname", unique = true, nullable = false)
    private String categoryName;

    @Column(name = "categorystatus", nullable = false)
    private Boolean categoryStatus;

    @OneToMany(mappedBy = "category")
    private Set<BookDetails> bookDetails;
}
