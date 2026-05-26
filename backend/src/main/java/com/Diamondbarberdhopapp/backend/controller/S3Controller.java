package com.Diamondbarberdhopapp.backend.controller;

import com.Diamondbarberdhopapp.backend.service.S3Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile) {

        try {
            // Convert MultipartFile -> File
            File file = convertMultiPartToFile(multipartFile);

            String fileName = multipartFile.getOriginalFilename();

            // Upload to S3
            String url = s3Service.uploadFile(fileName, file);

            // delete temp file
            file.delete();

            return url;

        } catch (Exception e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws Exception {
        File convFile = new File(file.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }

        return convFile;
    }
}