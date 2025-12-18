package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.response.DeleteProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.response.GetProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.response.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ProductResponseDTO create(CreateProductRequestDTO requestDTO, MultipartFile fileImage);

    Product save(Product product);

    Product getById(String id);

    ProductResponseDTO update(String productId, UpdateProductRequestDTO requestDTO);

    DeleteProductResponseDTO deleteById(String id);

    GetProductResponseDTO getProduct(String id);

    Page<GetProductResponseDTO> getProducts(Integer page, Integer size);
}
