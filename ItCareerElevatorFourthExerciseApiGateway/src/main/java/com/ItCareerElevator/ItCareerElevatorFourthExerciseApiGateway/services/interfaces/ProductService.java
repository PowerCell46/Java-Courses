package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.request.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.request.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response.DeleteProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ProductResponseDTO create(CreateProductRequestDTO requestDTO, MultipartFile fileImage);

    Product getById(String id);

    ProductResponseDTO update(String id, UpdateProductRequestDTO requestDTO);

    DeleteProductResponseDTO deleteById(String id);

    Page<ProductResponseDTO> getProducts(Integer page, Integer size);

    ProductResponseDTO getProduct(String id);
}
