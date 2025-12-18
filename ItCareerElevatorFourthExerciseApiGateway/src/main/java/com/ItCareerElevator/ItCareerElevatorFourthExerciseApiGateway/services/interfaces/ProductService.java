package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.request.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.request.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response.DeleteProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response.GetProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response.ProductResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    GetProductResponseDTO getProduct(String id);

    List<GetProductResponseDTO> getProducts(Integer page, Integer size);

    ProductResponseDTO create(CreateProductRequestDTO requestDTO, MultipartFile fileImage);

    ProductResponseDTO update(String id, UpdateProductRequestDTO requestDTO, MultipartFile fileImage);

    DeleteProductResponseDTO deleteById(String id);
}
