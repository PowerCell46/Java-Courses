package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.DeleteProductResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.GetProductResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.ProductResponseDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<GetProductResponseDTO> getProduct(@PathVariable String id) {
        log.info("---> GET request on /api/products/{}.", id);

        GetProductResponseDTO product = productService.getProductById(id);

        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<GetProductResponseDTO>> getProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        log.info("---> GET request on /api/products with page number '{}' and size '{}'.", page, size);

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
        log.info("---> POST request on /api/products.");

        ProductResponseDTO responseDTO = productService.create(requestDTO, fileImage);

        URI location = URI.create("/api/products/" + responseDTO.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable String id,
            @RequestPart("fileImage") MultipartFile fileImage,
            @RequestPart("requestDTO") UpdateProductRequestDTO requestDTO
    ) {
        log.info("---> PATCH request on /api/products/{}.", id);

        ProductResponseDTO responseDTO = productService.update(id, requestDTO, fileImage);

        return ResponseEntity.ok(responseDTO);
    }

    // TODO: increase product quantity {productId, restockQuantity (will be += to the current quantity)}

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteProductResponseDTO> deleteProduct(@PathVariable String id) {
        log.info("---> DELETE request on /api/products/{}.", id);

        DeleteProductResponseDTO responseDTO = productService.deleteById(id);

        return ResponseEntity.ok(responseDTO);
    }
}
