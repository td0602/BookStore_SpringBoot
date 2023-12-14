package com.example.ebookapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements StorageService{

    private final Path rootBookLocation;
    private final Path rootUserLocation;
    public FileSystemStorageService() {
        //gan duong link toi folder chua anh
        rootBookLocation = Paths.get("src/main/resources/static/uploads/books");
        rootUserLocation = Paths.get("src/main/resources/static/uploads/users");
    }

    @Override
    public void init() {
        try {
            //tao thu muc theo duong dan rootLocation
            Files.createDirectories(rootBookLocation);
            Files.createDirectories(rootUserLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void store(MultipartFile file, Path rootLocation) {
        try {
            Path destinationFile = rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING); //Neu ton tai se thay the
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void storeBookImage(MultipartFile file) {
        store(file, rootBookLocation);
    }

    @Override
    public void storeUserImage(MultipartFile file) {
        store(file, rootUserLocation);
    }
}
