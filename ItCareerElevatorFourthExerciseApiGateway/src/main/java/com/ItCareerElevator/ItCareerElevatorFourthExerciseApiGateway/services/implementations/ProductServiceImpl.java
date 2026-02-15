package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.request.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.request.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response.DeleteProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response.GetProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response.ProductResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.exceptions.msvc.ManagementMicroserviceException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final WebClient managementWebClient;

    @Override
    public GetProductResponseDTO getProduct(String id) {
        log.info("Making a request to the product (management) microservice.");

        return managementWebClient
                .get()
                .uri(String.format("/api/products/%s", id))
                .retrieve()
                .onStatus(HttpStatusCode::isError, // TODO: Look for a better approach (test all possible custom errors)
                        resp -> resp
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(ManagementMicroserviceException::new)
                                .flatMap(Mono::error)
                )
                .bodyToMono(GetProductResponseDTO.class)
                .block();
    }

    @Override
    public List<GetProductResponseDTO> getProducts(Integer page, Integer size) {
        log.info("Making a request to the product (management) microservice.");

        return managementWebClient
                .get()
                .uri(String.format("/api/products?page=%d&size=%d", page, size))
                .retrieve()
                .onStatus(HttpStatusCode::isError, // TODO: Look for a better approach (test all possible custom errors)
                        resp -> resp
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(ManagementMicroserviceException::new)
                                .flatMap(Mono::error)
                )
                .bodyToMono(new ParameterizedTypeReference<List<GetProductResponseDTO>>() {
                })
                .block();
    }

    @Override
    public ProductResponseDTO create(CreateProductRequestDTO requestDTO, MultipartFile fileImage) {
        log.info("Making a request to the product (management) microservice.");

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
    public ProductResponseDTO update(String id, UpdateProductRequestDTO requestDTO, MultipartFile fileImage) {
        log.info("Making a request to the product (management) microservice.");

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("requestDTO", requestDTO).contentType(MediaType.APPLICATION_JSON);
        builder.part("fileImage", fileImage.getResource())
                .filename(Objects.requireNonNull(fileImage.getOriginalFilename()))
                .contentType(MediaType.parseMediaType(Objects.requireNonNull(fileImage.getContentType())));

        return managementWebClient
                .patch()
                .uri(String.format("/api/products/%s", id))
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
    public DeleteProductResponseDTO deleteById(String id) {
        log.info("Making a request to the product (management) microservice.");

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
}
