package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.DeleteProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Product;

public interface ProductService {

    ProductResponseDTO create(CreateProductRequestDTO requestDTO);

    Product save(Product product);

    Product getById(String id);

    ProductResponseDTO update(String productId, UpdateProductRequestDTO requestDTO);

    DeleteProductResponseDTO deleteById(String id);
}
