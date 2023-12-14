package com.example.ebookapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();
    void storeBookImage(MultipartFile file);
    void storeUserImage(MultipartFile file);
}
