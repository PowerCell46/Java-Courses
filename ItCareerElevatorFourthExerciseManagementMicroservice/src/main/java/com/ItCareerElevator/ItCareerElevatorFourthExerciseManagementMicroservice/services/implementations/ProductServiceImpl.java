package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.response.DeleteProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.response.GetProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.response.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Producer;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.ProductTranslation;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.InvalidTranslationsException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.product.NoSuchProductException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.product.ProductAlreadyExistsException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories.ProductRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.ProducerService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.ProductService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.ProductTranslationService;

import static com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.utils.ImageUtils.getImageContentType;
import static com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.utils.ImageUtils.readImageToBase64;
import static com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.utils.ImageUtils.saveImageFileToFileSystem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String IMAGE_SUBDIRECTORY_NAME = "products";

    private final ObjectMapper objectMapper;
    private final ProducerService producerService;
    private final ProductRepository productRepository;
    private final ProductTranslationService productTranslationService;

    @Override
    public ProductResponseDTO create(CreateProductRequestDTO requestDTO, MultipartFile fileImage) {
        runCreateValidations(requestDTO);

        Product product = objectMapper.convertValue(requestDTO, Product.class);

        product.setImageUrl(saveImageFileToFileSystem(fileImage, IMAGE_SUBDIRECTORY_NAME));
        product.setProducer(producerService.getOrCreateByName(requestDTO.getProducerName()));
        if (product.getInStockQuantity() == null)
            product.setInStockQuantity(0);

        product = save(product);

        Set<ProductTranslation> translations = productTranslationService
                .createTranslations(requestDTO, product);
        product.setTranslations(translations);

        return constructProductResponseDTO(product);
    }

    private void runCreateValidations(CreateProductRequestDTO requestDTO) {
        if (requestDTO.getNameTranslations().isEmpty() || requestDTO.getDescriptionTranslations().isEmpty()) {
            throw new InvalidTranslationsException("Name translations and description translations cannot be empty.");
        }

        if (translatedNameAlreadyExists(requestDTO)) { // * Don't allow a product name to be a duplicate
            throw new ProductAlreadyExistsException(
                    "Cannot create product, because the name is already taken (in one or more language/s)."
            );
        }
    }

    private boolean translatedNameAlreadyExists(CreateProductRequestDTO requestDTO) {
        return requestDTO
                .getNameTranslations()
                .stream()
                .anyMatch(locale ->
                        !productRepository
                                .findAllByTranslationsNameAndIsDeletedIsFalse(locale.getTranslation())
                                .isEmpty()
                );
    }

    @Override
    public Product save(Product product) {
        log.info("Persisting product to the database.");

        return productRepository.save(product);
    }

    @Override
    public Product getById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new NoSuchProductException(String.format("No product found with id %s.", id));
        }

        return optionalProduct.get();
    }

    @Override
    public ProductResponseDTO update(String productId, UpdateProductRequestDTO requestDTO, MultipartFile fileImage) {
        Product product = getById(productId);

        if (
            // @formatter:off
                (requestDTO.getNameTranslations() != null && !requestDTO.getNameTranslations().isEmpty()) ||
                (requestDTO.getDescriptionTranslations() != null && !requestDTO.getDescriptionTranslations().isEmpty())
            // @formatter:on
        ) {
            productTranslationService.updateTranslations(requestDTO, product);
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
        if (fileImage != null && !fileImage.isEmpty()) {
            product.setImageUrl(saveImageFileToFileSystem(fileImage, IMAGE_SUBDIRECTORY_NAME));
        }

        if (
            // @formatter:off
                (requestDTO.getNameTranslations() != null && !requestDTO.getNameTranslations().isEmpty()) ||
                (requestDTO.getDescriptionTranslations() != null && !requestDTO.getDescriptionTranslations().isEmpty()) ||
                requestDTO.getProducerName() != null || requestDTO.getPrice() != null ||
                requestDTO.getInStockQuantity() != null || (fileImage != null && !fileImage.isEmpty())
            // @formatter:on
        ) {
            product = save(product);
        }

        return constructProductResponseDTO(product);
    }

    @Override
    public DeleteProductResponseDTO deleteById(String id) {
        Product product = getById(id);

        DeleteProductResponseDTO responseDTO = constructDeleteProductResponseDTO(product);

        log.info("(Soft) deleting product from the database.");
        productRepository.deleteById(id);

        return responseDTO;
    }

    @Override
    public GetProductResponseDTO getProduct(String id) {
        Product product = getById(id);

        return constructGetProductResponseDTO(product);
    }

    @Override
    public Page<GetProductResponseDTO> getProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findAll(pageable)
                .map(this::constructGetProductResponseDTO);
    }

    private ProductResponseDTO constructProductResponseDTO(Product product) {
        return objectMapper.convertValue(product, ProductResponseDTO.class);
    }

    private DeleteProductResponseDTO constructDeleteProductResponseDTO(Product product) {
        return objectMapper.convertValue(product, DeleteProductResponseDTO.class);
    }

    private GetProductResponseDTO constructGetProductResponseDTO(Product product) {
        Path imagePath = Path.of(product.getImageUrl());

        return GetProductResponseDTO
                .builder()
                .id(product.getId())
                .nameTranslations(product.getTranslations()
                        .stream()
                        .map(ProductTranslation::getName)
                        .toList()
                )
                .descriptionTranslations(product.getTranslations()
                        .stream()
                        .map(ProductTranslation::getDescription)
                        .toList()
                )
                .producerName(product.getProducer().getName())
                .inStockQuantity(product.getInStockQuantity())
                .price(product.getPrice())
                .imageName(imagePath.getFileName().toString())
                .imageBase64(readImageToBase64(imagePath))
                .imageContentType(getImageContentType(imagePath))
                .build();
    }
}
