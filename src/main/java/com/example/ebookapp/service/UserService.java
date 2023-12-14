package com.example.ebookapp.service;

import com.example.ebookapp.model.User;

public interface UserService {
    User findByUserName(String userName);
    Boolean edit(User user);

    Boolean create(User user);
}
