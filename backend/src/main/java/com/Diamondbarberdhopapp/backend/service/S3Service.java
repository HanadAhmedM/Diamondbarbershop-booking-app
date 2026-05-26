package com.Diamondbarberdhopapp.backend.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.File;

@Service
public class S3Service {

    private final S3Client s3Client;

    private final String bucketName = "diamond-barbershop-images-2026-hanad";

    public S3Service() {
        this.s3Client = S3Client.builder()
                .region(Region.EU_NORTH_1)
                .build();
    }

    public String uploadFile(String fileName, File file) {

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build(),
                RequestBody.fromFile(file)
        );

        return "https://" + bucketName + ".s3.eu-north-1.amazonaws.com/" + fileName;
    }
}
