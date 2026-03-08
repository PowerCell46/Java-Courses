package com.ItCareerElevatorSixthExercise.services.implementations.product;

import com.ItCareerElevatorSixthExercise.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.DeleteProductResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.GetProductResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.ProductResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.CommonEntity;
import com.ItCareerElevatorSixthExercise.entities.product.Manufacturer;
import com.ItCareerElevatorSixthExercise.entities.product.Product;
import com.ItCareerElevatorSixthExercise.entities.product.ProductTranslation;
import com.ItCareerElevatorSixthExercise.exceptions.product.NoSuchProductException;
import com.ItCareerElevatorSixthExercise.repositories.product.ProductRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.product.ManufacturerService;
import com.ItCareerElevatorSixthExercise.services.interfaces.product.MinioStorageService;
import com.ItCareerElevatorSixthExercise.services.interfaces.product.ProductService;
import com.ItCareerElevatorSixthExercise.services.interfaces.product.ProductTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String IMAGE_SUBDIRECTORY_NAME = "products";

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final MinioStorageService minioStorageService;
    private final ManufacturerService manufacturerService;
    private final ProductTranslationService productTranslationService;

    @Override
    public ProductResponseDTO create(CreateProductRequestDTO requestDTO, MultipartFile fileImage) {
        productTranslationService.validate(requestDTO);

        Product product = objectMapper.convertValue(requestDTO, Product.class);

        product.setImageUrl(minioStorageService.upload(fileImage, IMAGE_SUBDIRECTORY_NAME));
        product.setManufacturer(manufacturerService.getOrCreateByName(requestDTO.getManufacturerName()));
        if (product.getInStockQuantity() == null)
            product.setInStockQuantity(0);

        product = save(product);

        Set<ProductTranslation> translations = productTranslationService.create(requestDTO, product);
        product.setTranslations(translations);

        return new ProductResponseDTO(
                CommonEntity.convertIdToSnowflakeId(product.getId()),
                product.getPrice(),
                product.getInStockQuantity(),
                product.getImageUrl()
        );
    }

    @Override
    public Product save(Product product) {
        log.info("Persisting product to the database.");
        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getById(String id) {
        return productRepository
                .findById(CommonEntity.convertSnowflakeIdToId(id))
                .orElseThrow(() -> new NoSuchProductException(String.format("No product found with id %s.", id)));
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
    public ProductResponseDTO update(String id, UpdateProductRequestDTO requestDTO, MultipartFile fileImage) {
        Product product = getById(id);

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
            minioStorageService.delete(product.getImageUrl());
            product.setImageUrl(minioStorageService.upload(fileImage, IMAGE_SUBDIRECTORY_NAME));
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
        minioStorageService.delete(product.getImageUrl());

        var responseDTO = new DeleteProductResponseDTO(id);

        log.info("Deleting product from the database.");
        productRepository.delete(product);

        return responseDTO;
    }

    private GetProductResponseDTO constructGetProductResponseDTO(Product product) {
        return GetProductResponseDTO
                .builder()
                .id(CommonEntity.convertIdToSnowflakeId(product.getId()))
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
                .imageUrl(product.getImageUrl())
                .build();
    }
}
