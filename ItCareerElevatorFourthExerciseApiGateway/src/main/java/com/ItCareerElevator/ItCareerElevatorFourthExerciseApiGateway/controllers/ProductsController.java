package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.controllers;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.DeleteProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.UpdateProductRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductService productService;

    @PostMapping("/manage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestPart("fileImage") MultipartFile fileImage,
            @RequestPart("requestDTO") CreateProductRequestDTO requestDTO
    ) {
        ProductResponseDTO responseDTO = productService.create(requestDTO, fileImage);

        URI location = URI.create("/api/products/" + responseDTO.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PatchMapping("/manage/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct( // TODO: allow user to change an image?
                                                             @PathVariable String productId, @RequestBody UpdateProductRequestDTO requestDTO
    ) {
        ProductResponseDTO responseDTO = productService.update(productId, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/manage/{productId}")
    public ResponseEntity<DeleteProductResponseDTO> deleteProduct(@PathVariable String productId) {
        DeleteProductResponseDTO responseDTO = productService.deleteById(productId);

        return ResponseEntity.ok(responseDTO);
    }
}
