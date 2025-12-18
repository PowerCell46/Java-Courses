package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.response.DeleteProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.response.GetProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.response.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Producer;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.ProductTranslation;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.InvalidLocalesException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.NoSuchProductException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.ProductAlreadyExistsException;
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

    private static final String IMAGE_SUBDIRECTORY = "products";

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;

    private final ProducerService producerService;
    private final ProductTranslationService productTranslationService;

    @Override
    public ProductResponseDTO create(CreateProductRequestDTO requestDTO, MultipartFile fileImage) {
        runCreateValidations(requestDTO);

        Product product = objectMapper.convertValue(requestDTO, Product.class);

        product.setImageUrl(saveImageFileToFileSystem(fileImage, IMAGE_SUBDIRECTORY));
        product.setProducer(producerService.getOrCreateByName(requestDTO.getProducerName()));
        if (product.getInStockQuantity() == null)
            product.setInStockQuantity(0);

        product = save(product);

        Set<ProductTranslation> translations = productTranslationService.createTranslations(requestDTO, product);
        product.setTranslations(translations);

        return constructProductResponseDTO(product);
    }

    private void runCreateValidations(CreateProductRequestDTO requestDTO) {
        if (requestDTO.getNameLocales().isEmpty() || requestDTO.getDescriptionLocales().isEmpty()) {
            throw new InvalidLocalesException("Name locales and description locales cannot be empty.");
        }

        if (requestDTO.getNameLocales().size() != requestDTO.getDescriptionLocales().size()) {
            throw new InvalidLocalesException("Name locales and description locales don't match in size.");
        }

        if (translatedNameAlreadyExists(requestDTO)) { // Don't allow a product name to be a duplicate
            throw new ProductAlreadyExistsException(
                    String.format(
                            "Product with name %s already exists.",
                            requestDTO.getNameLocales().getFirst().getTranslation()
                    )
            );
        }
    }

    private boolean translatedNameAlreadyExists(CreateProductRequestDTO requestDTO) {
        return requestDTO
                .getNameLocales()
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
                (requestDTO.getNameLocales() != null && !requestDTO.getNameLocales().isEmpty()) ||
                (requestDTO.getDescriptionLocales() != null && !requestDTO.getDescriptionLocales().isEmpty())
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
            product.setImageUrl(saveImageFileToFileSystem(fileImage, IMAGE_SUBDIRECTORY));
        }

        if (
            // @formatter:off
                requestDTO.getNameLocales() != null ||
                requestDTO.getDescriptionLocales() != null || requestDTO.getProducerName() != null ||
                requestDTO.getPrice() != null || requestDTO.getInStockQuantity() != null ||
                (fileImage != null && !fileImage.isEmpty())
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
