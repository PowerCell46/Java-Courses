package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.controllers;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.DeleteProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.NoSuchProductException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody CreateProductRequestDTO requestDTO) {
        ProductResponseDTO responseDTO = productService.create(requestDTO);

        URI location = URI.create("/api/products/" + responseDTO.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable String productId, @RequestBody UpdateProductRequestDTO requestDTO
    ) {
        ProductResponseDTO responseDTO = productService.update(productId, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<DeleteProductResponseDTO> deleteProduct(@PathVariable String productId) {
        DeleteProductResponseDTO responseDTO = productService.deleteById(productId);

        return ResponseEntity.ok(responseDTO);
    }
}
