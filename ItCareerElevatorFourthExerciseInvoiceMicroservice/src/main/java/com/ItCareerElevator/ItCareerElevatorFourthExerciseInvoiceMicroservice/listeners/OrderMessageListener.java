package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.listeners;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMessageListener {

    private static final String INVOICED_KEY_PREFIX = "order:invoiced:";
    private static final long IDEMPOTENCY_TTL_HOURS = 3L;

    private final OrderService orderService;
    private final StringRedisTemplate redis;

    @KafkaListener(
            topics = "order",
            groupId = "order-invoice-consumer",
            containerFactory = "orderInvoiceContainerFactory"
    )
    public void handleOrderMessage(String orderId) {
        log.info("---> Handling message in topic order.");

        if (orderId == null) {
            log.error("Null orderId. Cannot continue with the invoice generation process.");
            return;
        }

        log.info("Received orderId from Kafka: {}.", orderId);

        if (isOrderAlreadyProcessed(orderId)) {
            log.info("Order {} already has an invoice that was successfully sent.", orderId);
            return;
        }

        orderService.sendPdfInvoiceThroughEmail(orderId);
        confirmOrderIsProcessed(orderId);
    }

    private boolean isOrderAlreadyProcessed(String orderId) {
        String orderKey = INVOICED_KEY_PREFIX + orderId;
        Boolean alreadyProcessed = redis.hasKey(orderKey);

        return Boolean.TRUE.equals(alreadyProcessed);
    }

    private void confirmOrderIsProcessed(String orderId) {
        String orderKey = INVOICED_KEY_PREFIX + orderId;
        redis.opsForValue().set(orderKey, "1", IDEMPOTENCY_TTL_HOURS, TimeUnit.HOURS);

        log.info("Order {} is successfully processed. Storing the orderKey in Redis with TTL {} hours.",
                orderId,
                IDEMPOTENCY_TTL_HOURS
        );
    }
}
