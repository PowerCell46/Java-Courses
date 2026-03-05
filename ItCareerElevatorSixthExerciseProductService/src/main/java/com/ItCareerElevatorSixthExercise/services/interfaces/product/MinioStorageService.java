package com.ItCareerElevatorSixthExercise.services.interfaces.product;

import org.springframework.web.multipart.MultipartFile;

public interface MinioStorageService {

    String upload(MultipartFile file, String subDirectory);

    void delete(String imageUrl);
}
