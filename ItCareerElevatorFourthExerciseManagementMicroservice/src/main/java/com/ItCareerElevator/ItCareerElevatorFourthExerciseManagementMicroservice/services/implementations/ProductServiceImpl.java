package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.LocaleRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.response.DeleteProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.response.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Producer;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.InvalidFileImageException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.NoSuchProductException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.ProductAlreadyExistsException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories.ProductRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.ProducerService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ObjectMapper objectMapper;
    private final ProducerService producerService;
    private final ProductRepository productRepository;

    @Override
    public ProductResponseDTO create(CreateProductRequestDTO requestDTO, MultipartFile fileImage) {
        if (translatedNameAlreadyExists(requestDTO)) {
            throw new ProductAlreadyExistsException(String.format(
                    "Product with name %s already exists.",
                    requestDTO.getNameLocales().getFirst().getTranslation()
            ));
        }

        Product product = objectMapper.convertValue(requestDTO, Product.class);

        product.setImageUrl(saveImageFileToFileSystem(fileImage));
        product.setProducer(producerService.getOrCreateByName(requestDTO.getProducerName()));
        if (product.getInStockQuantity() == null) product.setInStockQuantity(0);

        product = save(product);

        return constructProductResponseDTO(product);
    }

    private boolean translatedNameAlreadyExists(CreateProductRequestDTO requestDTO) {
        return requestDTO
                .getNameLocales()
                .stream()
                .anyMatch(locale ->
                        !productRepository.findAllByTranslationsNameAndIsDeletedIsFalse(locale.getTranslation()).isEmpty()
                );
    }

    private String saveImageFileToFileSystem(MultipartFile fileImage) {
        if (fileImage == null || fileImage.isEmpty()) {
            throw new InvalidFileImageException("Invalid image file.");
        }

        final Path uploadPath = Paths.get("uploads", "products");
        try {
            Files.createDirectories(uploadPath);

        } catch (IOException e) {
            throw new RuntimeException("Error occurred while creating the products upload directory.", e);
        }

        String originalFileName = fileImage.getOriginalFilename();
        String extension = "";

        final String EXTENSION_SEPARATOR = ".";
        if (originalFileName != null && originalFileName.contains(EXTENSION_SEPARATOR)) {
            extension = originalFileName.substring(originalFileName.lastIndexOf(EXTENSION_SEPARATOR));
        }

        String fileName = UUID.randomUUID() + extension;

        Path targetPath = uploadPath.resolve(fileName);

        try (InputStream is = new BufferedInputStream(fileImage.getInputStream())) {
            Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            throw new RuntimeException("Failed to store image file.", ex);
        }

        return uploadPath.resolve(fileName).toString();
    }

    @Override
    public Product save(Product product) {
//        log.info("Persisting product {} to the database.", product.getEnName());

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

//        if (requestDTO.getBgName() != null) {
//            product.setBgName(requestDTO.getBgName().strip());
//        }
//        if (requestDTO.getEnName() != null) {
//            product.setEnName(requestDTO.getEnName().strip());
//        }
//        if (requestDTO.getBgDescription() != null) {
//            product.setBgDescription(requestDTO.getBgDescription().strip());
//        }
//        if (requestDTO.getEnDescription() != null) {
//            product.setEnDescription(requestDTO.getEnDescription().strip());
//        }
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

//        log.info("(Soft) deleting product {} from the database.", product.getEnName());
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
