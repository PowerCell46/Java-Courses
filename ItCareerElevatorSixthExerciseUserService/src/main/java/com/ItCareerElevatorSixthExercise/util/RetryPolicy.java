package com.ItCareerElevatorSixthExercise.util;

import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevatorSixthExercise.exceptions.PaymentServiceException;
import reactor.util.retry.Retry;

import java.time.Duration;

public class RetryPolicy {

    public static Retry buildRetrySpec() {
        return Retry
                .backoff(4, Duration.ofSeconds(2)) // 2s, 4s, 8s, 16s
                .maxBackoff(Duration.ofSeconds(20))
                .jitter(0.5d) // 50% jitter
                .filter(RetryPolicy::isRetriable)
                .onRetryExhaustedThrow((spec, signal) -> {
                    Throwable failure = signal.failure();

                    ErrorResponseDTO error = new ErrorResponseDTO(
                            500,
                            failure.getMessage() != null ? failure.getMessage() : "Internal server error occurred.",
                            System.currentTimeMillis()
                    );

                    return new PaymentServiceException(error);
                });
    }

    private static boolean isRetriable(Throwable throwable) {
        return isNetworkIssue(throwable) || isTransientHttpResponse(throwable);
    }

    private static boolean isNetworkIssue(Throwable throwable) {
        return
                // @formatter:off
                    throwable instanceof java.net.ConnectException ||
                    throwable instanceof java.util.concurrent.TimeoutException ||
                    throwable instanceof org.springframework.web.reactive.function.client.WebClientRequestException ||
                    throwable.getCause() instanceof java.net.SocketException;
                // @formatter:on
    }

    private static boolean isTransientHttpResponse(Throwable throwable) {
        if (throwable instanceof org.springframework.web.reactive.function.client.WebClientResponseException ex) {
            int status = ex.getStatusCode().value();

            return status == 502 || status == 503 || status == 504;
        }

        return false;
    }
}
