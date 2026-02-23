package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.product.request.CreateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.product.request.UpdateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.product.response.DeleteProductResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.product.response.GetProductResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.product.response.ProductResponseDTO;
import com.ItCareerElevatorSixthExercise.exceptions.msvc.ProductServiceException;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
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

import static com.ItCareerElevatorSixthExercise.utils.common.RetryPolicy.buildRetrySpec;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final WebClient productServiceWebClient;

    @Override
    public ProductResponseDTO create(CreateProductRequestDTO requestDTO, MultipartFile fileImage) {
        log.info("---| Making a request to the productService.");

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("requestDTO", requestDTO).contentType(MediaType.APPLICATION_JSON);
        builder.part("fileImage", fileImage.getResource())
                .filename(Objects.requireNonNull(fileImage.getOriginalFilename()))
                .contentType(MediaType.parseMediaType(Objects.requireNonNull(fileImage.getContentType())));

        return productServiceWebClient
                .post()
                .uri("/api/products")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(ProductServiceException::new)
                                .flatMap(Mono::error))
                .bodyToMono(ProductResponseDTO.class)
                .retryWhen(buildRetrySpec())
                .block();
    }

    @Override
    public GetProductResponseDTO getProductById(String id) {
        log.info("---| Making a request to the productService.");

        return productServiceWebClient
                .get()
                .uri(String.format("/api/products/%s", id))
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(ProductServiceException::new)
                                .flatMap(Mono::error))
                .bodyToMono(GetProductResponseDTO.class)
                .retryWhen(buildRetrySpec())
                .block();
    }

    @Override
    public List<GetProductResponseDTO> getProducts(Integer page, Integer size) {
        log.info("---| Making a request to the productService.");

        return productServiceWebClient
                .get()
                .uri(String.format("/api/products?page=%d&size=%d", page, size))
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(ProductServiceException::new)
                                .flatMap(Mono::error))
                .bodyToMono(new ParameterizedTypeReference<List<GetProductResponseDTO>>() {
                })
                .retryWhen(buildRetrySpec())
                .block();
    }

    @Override
    public ProductResponseDTO update(String id, UpdateProductRequestDTO requestDTO, MultipartFile fileImage) {
        log.info("---| Making a request to the productService.");

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("requestDTO", requestDTO).contentType(MediaType.APPLICATION_JSON);

        if (fileImage != null && !fileImage.isEmpty()) {
            builder
                    .part("fileImage", fileImage.getResource())
                    .filename(Objects.requireNonNull(fileImage.getOriginalFilename()))
                    .contentType(MediaType.parseMediaType(Objects.requireNonNull(fileImage.getContentType())));
        }

        return productServiceWebClient
                .patch()
                .uri(String.format("/api/products/%s", id))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(ProductServiceException::new)
                                .flatMap(Mono::error))
                .bodyToMono(ProductResponseDTO.class)
                .retryWhen(buildRetrySpec())
                .block();
    }

    @Override
    public DeleteProductResponseDTO deleteById(String id) {
        log.info("---| Making a request to the productService.");

        return productServiceWebClient
                .delete()
                .uri(String.format("/api/products/%s", id))
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(ProductServiceException::new)
                                .flatMap(Mono::error))
                .bodyToMono(DeleteProductResponseDTO.class)
                .retryWhen(buildRetrySpec())
                .block();
    }
}
