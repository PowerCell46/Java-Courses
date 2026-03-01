package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.DeleteProductResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.GetImageResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.GetProductResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.ProductResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.CommonEntity;
import com.ItCareerElevatorSixthExercise.entities.product.Manufacturer;
import com.ItCareerElevatorSixthExercise.entities.product.Product;
import com.ItCareerElevatorSixthExercise.entities.product.ProductTranslation;
import com.ItCareerElevatorSixthExercise.exceptions.product.NoSuchProductException;
import com.ItCareerElevatorSixthExercise.repositories.ProductRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.ManufacturerService;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProductService;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProductTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.ItCareerElevatorSixthExercise.util.ImageUtils.getImageContentType;
import static com.ItCareerElevatorSixthExercise.util.ImageUtils.readImageToBase64;
import static com.ItCareerElevatorSixthExercise.util.ImageUtils.saveImageFileToFileSystem;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String IMAGE_SUBDIRECTORY_NAME = "products";

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final ManufacturerService manufacturerService;
    private final ProductTranslationService productTranslationService;

    @Override
    public ProductResponseDTO create(CreateProductRequestDTO requestDTO, MultipartFile fileImage) {
        productTranslationService
                .validate(requestDTO.getNameTranslations());

        Product product = objectMapper.convertValue(requestDTO, Product.class);

        product.setImageUrl(saveImageFileToFileSystem(fileImage, IMAGE_SUBDIRECTORY_NAME));
        product.setManufacturer(manufacturerService.getOrCreateByName(requestDTO.getManufacturerName()));
        if (product.getInStockQuantity() == null)
            product.setInStockQuantity(0);

        product = save(product);

        Set<ProductTranslation> translations = productTranslationService
                .create(requestDTO, product);
        product.setTranslations(translations);

        return objectMapper.convertValue(product, ProductResponseDTO.class);
    }

    @Override
    public Product save(Product product) {
        log.info("Persisting product to the database.");

        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getById(String id) {
        Optional<Product> optionalProduct = productRepository
                .findById(CommonEntity.convertSnowflakeIdToId(id));

        if (optionalProduct.isEmpty()) {
            throw new NoSuchProductException(String.format("No product found with id %s.", id));
        }

        return optionalProduct.get();
    }

    @Override
    @Transactional(readOnly = true)
    public GetProductResponseDTO getProductById(String id) {
        Product product = getById(id);

        return constructGetProductResponseDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetProductResponseDTO> getProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findAll(pageable)
                .map(this::constructGetProductResponseDTO)
                .getContent();
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
            productTranslationService.update(requestDTO, product);
        }
        if (requestDTO.getManufacturerName() != null) {
            Manufacturer producer = manufacturerService.getOrCreateByName(requestDTO.getManufacturerName());
            product.setManufacturer(producer);
        }
        if (requestDTO.getPrice() != null) {
            product.setPrice(requestDTO.getPrice());
        }
        if (fileImage != null && !fileImage.isEmpty()) {
            product.setImageUrl(saveImageFileToFileSystem(fileImage, IMAGE_SUBDIRECTORY_NAME));
        }

        if (
            // @formatter:off
                (requestDTO.getNameTranslations() != null && !requestDTO.getNameTranslations().isEmpty()) ||
                (requestDTO.getDescriptionTranslations() != null && !requestDTO.getDescriptionTranslations().isEmpty()) ||
                requestDTO.getManufacturerName() != null || requestDTO.getPrice() != null || (fileImage != null && !fileImage.isEmpty())
            // @formatter:on
        ) {
            product = save(product);
        }

        return objectMapper.convertValue(product, ProductResponseDTO.class);
    }

    @Override
    public DeleteProductResponseDTO deleteById(String id) {
        Product product = getById(id);

        DeleteProductResponseDTO responseDTO = objectMapper.convertValue(product, DeleteProductResponseDTO.class);

        log.info("(Deleting product from the database.");
        productRepository.deleteById(CommonEntity.convertSnowflakeIdToId(id));

        return responseDTO;
    }

    private GetProductResponseDTO constructGetProductResponseDTO(Product product) {
        Path imagePath = Path.of(product.getImageUrl());

        return GetProductResponseDTO
                .builder()
                .id(product.getSnowflakeId())
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
                .manufacturerName(product.getManufacturer().getName())
                .inStockQuantity(product.getInStockQuantity())
                .price(product.getPrice())
                .image(GetImageResponseDTO
                        .builder()
                        .name(imagePath.getFileName().toString())
                        .contentType(getImageContentType(imagePath))
                        .base64(readImageToBase64(imagePath))
                        .build())
                .build();
    }
}
