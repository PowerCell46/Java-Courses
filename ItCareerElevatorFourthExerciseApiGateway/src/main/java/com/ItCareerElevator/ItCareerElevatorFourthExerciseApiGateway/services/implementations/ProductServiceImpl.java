package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.request.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.request.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response.DeleteProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.entities.ProductTranslation;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.exceptions.ManagementMicroserviceException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.exceptions.NoSuchProductException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.repositories.ProductRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final WebClient managementWebClient;

    @Override
    public ProductResponseDTO create(CreateProductRequestDTO requestDTO, MultipartFile fileImage) {
        log.info("Making a request to the management microservice.");

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("requestDTO", requestDTO).contentType(MediaType.APPLICATION_JSON);
        builder.part("fileImage", fileImage.getResource())
                .filename(Objects.requireNonNull(fileImage.getOriginalFilename()))
                .contentType(MediaType.parseMediaType(Objects.requireNonNull(fileImage.getContentType())));

        return managementWebClient
                .post()
                .uri("/api/products")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .onStatus(HttpStatusCode::isError, // TODO: Look for a better approach (test all possible custom errors)
                        resp -> resp
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(ManagementMicroserviceException::new)
                                .flatMap(Mono::error)
                )
                .bodyToMono(ProductResponseDTO.class)
                .block();
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
    public ProductResponseDTO update(String id, UpdateProductRequestDTO requestDTO) {
        log.info("Making a request to the management microservice.");

        return managementWebClient
                .patch()
                .uri(String.format("/api/products/%s", id))
                .bodyValue(requestDTO)
                .retrieve()
                .onStatus(HttpStatusCode::isError, // TODO: Look for a better approach (test all possible custom errors)
                        resp -> resp
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(ManagementMicroserviceException::new)
                                .flatMap(Mono::error)
                )
                .bodyToMono(ProductResponseDTO.class)
                .block();
    }

    @Override
    public DeleteProductResponseDTO deleteById(String id) {
        log.info("Making a request to the management microservice.");

        return managementWebClient
                .method(HttpMethod.DELETE)
                .uri(String.format("/api/products/%s", id))
                .retrieve()
                .onStatus(HttpStatusCode::isError, // TODO: Look for a better approach (test all possible custom errors)
                        resp -> resp
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(ManagementMicroserviceException::new)
                                .flatMap(Mono::error)
                )
                .bodyToMono(DeleteProductResponseDTO.class)
                .block();
    }

    @Override
    public Page<ProductResponseDTO> getProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findAllByIsDeletedIsFalse(pageable)
                .map(this::constructProductResponseDTO);
    }

    @Override
    public ProductResponseDTO getProduct(String id) {
        Product product = getById(id);

        return constructProductResponseDTO(product);
    }

    private ProductResponseDTO constructProductResponseDTO(Product product) {
        ProductResponseDTO responseDTO = objectMapper.convertValue(product, ProductResponseDTO.class);
        responseDTO.setProducerName(product.getProducer().getName());

        ProductTranslation translation = product.getTranslations()
                .stream()
                .filter(tr -> !tr.getIsDeleted())
                .findFirst()
                .orElse(null);

        responseDTO.setName(translation != null ? translation.getName() : "N/A");
        responseDTO.setDescription(translation != null ? translation.getDescription() : "N/A");

        return responseDTO;
    }
}
