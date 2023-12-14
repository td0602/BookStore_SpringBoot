package com.example.ebookapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "image")
    private String image;
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
    @Column(name = "fullname", nullable = false)
    private String fullName;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "phonenumber", unique = true)
    private String phoneNumber;
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles;
    @OneToMany(mappedBy = "user")
    private Set<BookDetails> bookDetails;
}
