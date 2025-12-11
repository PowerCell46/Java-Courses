package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Product;

public interface ProductService {

    ProductResponseDTO create(CreateProductRequestDTO requestDTO);

    Product save(Product product);
}
