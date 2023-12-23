package com.example.ebookapp.test.upload_file;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@Service
public class ImageStoregeService implements IStorageService {

    private final Path storageUserFolder = Paths.get("src/main/resources/static/uploads/users");
    private final Path storageBookFolder = Paths.get("src/main/resources/static/uploads/books");

    public ImageStoregeService() {
        try {
            Files.createDirectories(storageUserFolder);
            Files.createDirectories(storageBookFolder);
        } catch (IOException e) {
            throw new RuntimeException("Cannot initialize storage", e);
        }
    }

    //kiem tra co phai file image
    private boolean isImageFile(MultipartFile file) {
        //let install FileNameUtils: lay ra file Extension -> de lay ra cau hinh file xem do la gi
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        //chi cho phep 1 so duoi file sau la file image
        return Arrays.asList(new String[] {"png", "jpg", "jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase()); //kiem tra duoi file do co nam trong nhung duoi file kia khong
    }
    public String storeFile(MultipartFile file, Path path) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            if(!isImageFile(file)) {
                throw new RuntimeException("You can only upload image file");
            }
            float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
            if (fileSizeInMegabytes > 5.0f) {
                throw new RuntimeException("File must be <= 5Mb");
            }

            Path destinationFilePath = path.resolve( Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals (path.toAbsolutePath())) {
                throw new RuntimeException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) { //copy file vao destinationFilePath, REPLACE_EXISTING: neu da ton tai thi thay the
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return file.getOriginalFilename();

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Override
    public String storeBookImage(MultipartFile file) {
        return storeFile(file, storageBookFolder);
    }

    @Override
    public String storeUserImage(MultipartFile file) {
        return storeFile(file, storageUserFolder);
    }
}
