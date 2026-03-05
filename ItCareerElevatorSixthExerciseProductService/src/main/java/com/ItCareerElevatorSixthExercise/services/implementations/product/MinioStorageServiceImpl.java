package com.ItCareerElevatorSixthExercise.services.implementations.product;

import com.ItCareerElevatorSixthExercise.exceptions.image.InvalidFileImageException;
import com.ItCareerElevatorSixthExercise.exceptions.image.ProcessImageFileException;
import com.ItCareerElevatorSixthExercise.services.interfaces.product.MinioStorageService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.SetBucketPolicyArgs;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioStorageServiceImpl implements MinioStorageService {

    @Value("${minio.bucket-name}")
    private String BUCKET_NAME;

    @Value("${minio.endpoint}")
    private String BASE_ENDPOINT;

    private final MinioClient minioClient;

    @PostConstruct
    public void initBucket() {
        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(BUCKET_NAME).build()
            );

            if (!exists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(BUCKET_NAME).build()
                );
                setPublicReadPolicy();
                log.info("Bucket '{}' created with public-read policy.", BUCKET_NAME);
            }
        } catch (Exception e) {
            log.error("MinIO init failed: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize MinIO bucket.", e);
        }
    }

    @Override
    public String upload(MultipartFile file, String subDirectory) {
        if (file == null || file.isEmpty()) {
            throw new InvalidFileImageException("Invalid image file.");
        }

        String extension = extractExtension(file.getOriginalFilename());
        String objectKey = subDirectory + "/" + UUID.randomUUID() + extension;
        String contentType = file.getContentType() != null ? file.getContentType() : "application/octet-stream";

        try (InputStream inputStream = new BufferedInputStream(file.getInputStream())) {
            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(BUCKET_NAME)
                            .object(objectKey)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(contentType)
                            .build()

            );

        } catch (Exception ex) {
            log.error("Failed to upload image to MinIO.", ex);
            throw new ProcessImageFileException("A problem occurred on the server side.");
        }

        return BASE_ENDPOINT + "/" + BUCKET_NAME + "/" + objectKey;
    }

    @Override
    public void delete(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank())
            return;

        String objectKey = extractObjectKey(imageUrl);

        try {
            minioClient.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(BUCKET_NAME)
                            .object(objectKey)
                            .build()
            );
            log.info("Deleted image: {}.", objectKey);

        } catch (Exception ex) {
            log.warn("Failed to delete image from MinIO: {}.", objectKey, ex);
        }
    }

    private String extractObjectKey(String imageUrl) {
        String prefix = BASE_ENDPOINT + "/" + BUCKET_NAME + "/";
        return imageUrl.substring(prefix.length());
    }

    private String extractExtension(String originalFileName) {
        if (originalFileName != null && originalFileName.contains("."))
            return originalFileName.substring(originalFileName.lastIndexOf("."));
        return "";
    }

    private void setPublicReadPolicy() throws Exception {
        String policy = """
                {
                    "Version": "2012-10-17",
                    "Statement": [{
                        "Effect": "Allow",
                        "Principal": "*",
                        "Action": "s3:GetObject",
                        "Resource": "arn:aws:s3:::%s/*"
                    }]
                }
                """.formatted(BUCKET_NAME);

        minioClient.setBucketPolicy(
                SetBucketPolicyArgs
                        .builder()
                        .config(policy)
                        .build()
        );
    }
}
