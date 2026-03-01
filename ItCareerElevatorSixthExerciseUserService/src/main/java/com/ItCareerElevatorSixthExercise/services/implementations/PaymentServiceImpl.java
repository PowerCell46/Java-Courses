package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.auth.request.DepositAmountRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.msvc.MsvcCryptoWalletRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.msvc.MsvcDepositRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.msvc.MsvcUpdateCryptoWalletRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.DepositAmountResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevatorSixthExercise.exceptions.PaymentServiceException;
import com.ItCareerElevatorSixthExercise.services.interfaces.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.ItCareerElevatorSixthExercise.util.RetryPolicy.buildRetrySpec;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final WebClient paymentServiceWebClient;

    @Override
    public void initializeCryptoWalletAddress(String id, String walletAddress) {
        log.info("---| Making a request to the paymentMicroservice.");

        var requestBody = new MsvcCryptoWalletRequestDTO(id, walletAddress);
        paymentServiceWebClient
                .post()
                .uri("/api/users-wallets/crypto")
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(PaymentServiceException::new)
                                .flatMap(Mono::error))
                .toBodilessEntity()
                .retryWhen(buildRetrySpec())
                .block();
    }

    @Override
    public void updateCryptoWalletAddress(String id, String walletAddress) {
        log.info("---| Making a request to the paymentMicroservice.");

        var requestBody = new MsvcUpdateCryptoWalletRequestDTO(walletAddress);

        paymentServiceWebClient
                .patch()
                .uri(String.format("/api/users-wallets/crypto/%s", id))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(PaymentServiceException::new)
                                .flatMap(Mono::error))
                .toBodilessEntity()
                .retryWhen(buildRetrySpec())
                .block();
    }

    @Override
    public DepositAmountResponseDTO depositAmount(DepositAmountRequestDTO requestDTO) {
        log.info("---| Making a request to the paymentMicroservice.");

        var requestBody = new MsvcDepositRequestDTO(requestDTO.getAmount());

        return paymentServiceWebClient
                .patch()
                .uri(String.format("/api/users-wallets/local/%s", requestDTO.getId()))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(PaymentServiceException::new)
                                .flatMap(Mono::error))
                .bodyToMono(DepositAmountResponseDTO.class)
                .retryWhen(buildRetrySpec())
                .block();
    }
}
