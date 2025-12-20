package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.utils;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.image.InvalidFileImageException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.image.ProcessImageFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

@Slf4j
public class ImageUtils {

    public static String saveImageFileToFileSystem(MultipartFile fileImage, String subDirectory) {
        if (fileImage == null || fileImage.isEmpty()) {
            throw new InvalidFileImageException("Invalid image file.");
        }

        final Path uploadPath = Paths.get("uploads", subDirectory);
        try {
            Files.createDirectories(uploadPath);

        } catch (IOException e) {
            log.error("Error occurred while creating the upload subdirectory.");

            throw new ProcessImageFileException("A problem occurred on the server side.");
        }

        String originalFileName = fileImage.getOriginalFilename();
        String extension = "";

        final String EXTENSION_SEPARATOR = ".";
        if (originalFileName != null && originalFileName.contains(EXTENSION_SEPARATOR)) {
            extension = originalFileName.substring(originalFileName.lastIndexOf(EXTENSION_SEPARATOR));
        }

        String fileName = UUID.randomUUID() + extension;

        Path targetPath = uploadPath.resolve(fileName);

        try (InputStream is = new BufferedInputStream(fileImage.getInputStream())) {
            Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            log.error("Failed to store image file.");

            throw new ProcessImageFileException("A problem occurred on the server side.");
        }

        return uploadPath.resolve(fileName).toString();
    }

    public static String readImageToBase64(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            return Base64.getEncoder().encodeToString(bytes);

        } catch (IOException e) {
            log.error("Error reading the image.");

            throw new ProcessImageFileException("A problem occurred on the server side.");
        }
    }

    public static String getImageContentType(Path path) {
        try {
            return Files.probeContentType(path);

        } catch (IOException e) {
            log.warn("Error reading the content-type of the image.");

            throw new ProcessImageFileException("A problem occurred on the server side.");
        }
    }
}
