package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.DeleteProductResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.GetProductResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.ProductResponseDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<GetProductResponseDTO> getProduct(@PathVariable String productId) {
        GetProductResponseDTO product = productService.getProduct(productId);

        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<GetProductResponseDTO>> getProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        List<GetProductResponseDTO> pageResult = productService.getProducts(page, size);

        return ResponseEntity.ok(pageResult);
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestPart("fileImage") MultipartFile fileImage,
            @RequestPart("requestDTO") CreateProductRequestDTO requestDTO
    ) {
        ProductResponseDTO responseDTO = productService.create(requestDTO, fileImage);

        URI location = URI.create("/api/products/" + responseDTO.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PatchMapping(
            value = "/{productId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable String productId,
            @RequestPart("fileImage") MultipartFile fileImage,
            @RequestPart("requestDTO") UpdateProductRequestDTO requestDTO
    ) {
        ProductResponseDTO responseDTO = productService.update(productId, requestDTO, fileImage);

        return ResponseEntity.ok(responseDTO);
    }

    // increase product quantity {productId, restockQuantity (will be += to the current quantity)}

    @DeleteMapping("/{productId}")
    public ResponseEntity<DeleteProductResponseDTO> deleteProduct(@PathVariable String productId) {
        DeleteProductResponseDTO responseDTO = productService.deleteById(productId);

        return ResponseEntity.ok(responseDTO);
    }
}
