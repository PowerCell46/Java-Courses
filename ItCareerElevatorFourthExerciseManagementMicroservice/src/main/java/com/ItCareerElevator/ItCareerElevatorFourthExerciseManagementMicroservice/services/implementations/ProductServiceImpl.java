package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.DeleteProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Producer;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.NoSuchProductException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.ProductAlreadyExistsException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories.ProductRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.ProducerService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

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
                productRepository.findByEnNameAndIsDeletedIsFalse(requestDTO.getEnName()).isPresent() ||
                productRepository.findByBgNameAndIsDeletedIsFalse(requestDTO.getBgName()).isPresent()
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

    @Override
    public Product getById(String id) {
        Optional<Product> optionalProduct = productRepository.findByIdAndIsDeletedIsFalse(id);

        if (optionalProduct.isEmpty()) {
            throw new NoSuchProductException(String.format("No product found with id %s.", id));
        }

        return optionalProduct.get();
    }

    @Override
    public ProductResponseDTO update(String productId, UpdateProductRequestDTO requestDTO) {
        Product product = getById(productId);

        if (requestDTO.getBgName() != null) {
            product.setBgName(requestDTO.getBgName().strip());
        }
        if (requestDTO.getEnName() != null) {
            product.setEnName(requestDTO.getEnName().strip());
        }
        if (requestDTO.getBgDescription() != null) {
            product.setBgDescription(requestDTO.getBgDescription().strip());
        }
        if (requestDTO.getEnDescription() != null) {
            product.setEnDescription(requestDTO.getEnDescription().strip());
        }
        if (requestDTO.getProducerName() != null) {
            Producer producer = producerService.getOrCreateByName(requestDTO.getProducerName());
            product.setProducer(producer);
        }
        if (requestDTO.getPrice() != null) {
            product.setPrice(requestDTO.getPrice());
        }
        if (requestDTO.getInStockQuantity() != null) {
            product.setInStockQuantity(requestDTO.getInStockQuantity());
        }

        if (
            // @formatter:off
                requestDTO.getBgName() != null || requestDTO.getEnName() != null ||
                requestDTO.getBgDescription() != null || requestDTO.getEnDescription() != null |
                requestDTO.getProducerName() != null || requestDTO.getPrice() != null ||
                requestDTO.getInStockQuantity() != null
            // @formatter:on
        ) {
            product = save(product);
        }

        return constructProductResponseDTO(product);
    }

    @Override
    public DeleteProductResponseDTO deleteById(String id) {
        Product product = getById(id);

        log.info("(Soft) deleting product {} from the database.", product.getEnName());
        productRepository.deleteById(id);

        return constructDeleteProductResponseDTO(product);
    }

    private ProductResponseDTO constructProductResponseDTO(Product product) {
        ProductResponseDTO responseDTO = objectMapper.convertValue(product, ProductResponseDTO.class);
        responseDTO.setProducerName(product.getProducer().getName());

        return responseDTO;
    }

    private DeleteProductResponseDTO constructDeleteProductResponseDTO(Product product) {
        return objectMapper.convertValue(product, DeleteProductResponseDTO.class);
    }
}
