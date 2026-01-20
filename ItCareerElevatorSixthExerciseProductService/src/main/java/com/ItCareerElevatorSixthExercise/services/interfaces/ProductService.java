package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.DeleteProductResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.GetProductResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.ProductResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductResponseDTO create(CreateProductRequestDTO requestDTO, MultipartFile fileImage);

    Product save(Product product);

    Product getById(String id);

    GetProductResponseDTO getProduct(String id);

    List<GetProductResponseDTO> getProducts(Integer page, Integer size);

    ProductResponseDTO update(String productId, UpdateProductRequestDTO requestDTO, MultipartFile fileImage);

    DeleteProductResponseDTO deleteById(String id);
}
