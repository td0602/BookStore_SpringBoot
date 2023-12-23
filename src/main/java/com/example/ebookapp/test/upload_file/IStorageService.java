package com.example.ebookapp.test.upload_file;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    String storeBookImage(MultipartFile file);
    String storeUserImage(MultipartFile file);
}
