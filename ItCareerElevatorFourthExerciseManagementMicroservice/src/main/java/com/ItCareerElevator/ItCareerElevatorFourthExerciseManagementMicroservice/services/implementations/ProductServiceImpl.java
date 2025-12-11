package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.ProductAlreadyExistsException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories.ProductRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.ProducerService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ObjectMapper objectMapper;
    private final ProducerService producerService;
    private final ProductRepository productRepository;

    @Override
    public ProductResponseDTO create(CreateProductRequestDTO requestDTO) {
        if (
            // @formatter:off
                productRepository.findByEnName(requestDTO.getEnName()).isPresent() ||
                productRepository.findByBgName(requestDTO.getBgName()).isPresent()
            // @formatter:on
        ) {
            throw new ProductAlreadyExistsException(String.format(
                    "Product with name %s | %s already exists.",
                    requestDTO.getEnName(),
                    requestDTO.getBgName()
            ));
        }

        Product product = objectMapper.convertValue(requestDTO, Product.class);
        product.setProducer(producerService.getOrCreateByName(requestDTO.getProducerName()));
        if (product.getInStockQuantity() == null) product.setInStockQuantity(0);

        product = save(product);

        return constructProductResponseDTO(product);
    }

    @Override
    public Product save(Product product) {
        log.info("Persisting product {} to the database.", product.getEnName());

        return productRepository.save(product);
    }

    private ProductResponseDTO constructProductResponseDTO(Product product) {
        ProductResponseDTO responseDTO = objectMapper.convertValue(product, ProductResponseDTO.class);
        responseDTO.setProducerName(product.getProducer().getName());

        return responseDTO;
    }
}
