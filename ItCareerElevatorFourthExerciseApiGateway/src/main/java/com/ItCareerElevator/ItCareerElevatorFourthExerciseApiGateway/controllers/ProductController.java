package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.controllers;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.request.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response.DeleteProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response.GetProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.request.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.interfaces.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<GetProductResponseDTO> getProduct(
            @PathVariable("productId")
            @NotNull(message = "Product id must not be null.")
            @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Invalid productId.")
            String productId
    ) {
        log.info("---> GET request on api/products/{}.", productId);

        GetProductResponseDTO responseDTO = productService.getProduct(productId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<GetProductResponseDTO>> getProducts(
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Page index cannot be negative.")
            Integer page,
            @RequestParam(defaultValue = "20")
            @Min(value = 1, message = "Size must be at least 1.")
            @Max(value = 100, message = "Size cannot exceed 100.")
            Integer size
    ) {
        log.info("---> GET request on api/products.");

        List<GetProductResponseDTO> responseDTOs = productService.getProducts(page, size);
        return ResponseEntity.ok(responseDTOs);
    }

    @PostMapping(
            value = "/manage",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestPart("fileImage") MultipartFile fileImage,
            @Valid @RequestPart("requestDTO") CreateProductRequestDTO requestDTO
    ) {
        log.info("---> POST request on /api/products/manage.");

        ProductResponseDTO responseDTO = productService.create(requestDTO, fileImage);

        URI location = URI.create("/api/products/" + responseDTO.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PatchMapping(
            value = "/manage/{productId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable String productId,
            @RequestPart("fileImage") MultipartFile fileImage,
            @Valid @RequestPart("requestDTO") UpdateProductRequestDTO requestDTO
    ) {
        log.info("---> PATCH request on /api/products/manage/{}.", productId);

        ProductResponseDTO responseDTO = productService.update(productId, requestDTO, fileImage);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/manage/{productId}")
    public ResponseEntity<DeleteProductResponseDTO> deleteProduct(
            @PathVariable("productId")
            @NotNull(message = "ProductId must not be null.")
            @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Invalid productId.")
            String productId
    ) {
        log.info("---> DELETE request on /api/products/manage/{}.", productId);

        DeleteProductResponseDTO responseDTO = productService.deleteById(productId);
        return ResponseEntity.ok(responseDTO);
    }
}
