package com.example.ebookapp.service;

import com.example.ebookapp.model.Role;
import com.example.ebookapp.model.User;
import com.example.ebookapp.model.UserRole;
import com.example.ebookapp.repository.RoleRepository;
import com.example.ebookapp.repository.UserRepository;
import com.example.ebookapp.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Boolean edit(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean create(User user) {
        try {
            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
            userRepository.save(user);
            Role role = roleRepository.findByName("USER");
            UserRole userRole = new UserRole(user, role);
            userRoleRepository.save(userRole);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
