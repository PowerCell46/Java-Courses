package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.request.PaymentVerificationRequest;
import com.ItCareerElevatorSixthExercise.DTOs.response.PaymentInstructionsResponse;
import com.ItCareerElevatorSixthExercise.DTOs.response.PaymentVerificationResponse;
import com.ItCareerElevatorSixthExercise.services.interfaces.PaymentService;
import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
w
    private final OrderService orderService;
    private final PaymentService paymentService;

    @Value("${ethereum.wallet.address}")
    private String walletAddress;

    public PaymentController(CryptoPaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    /**
     * Get payment instructions for an order
     */
    @GetMapping("/instructions/{orderId}")
    public PaymentInstructionsResponse getPaymentInstructions(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        BigDecimal amountInEth = convertToEth(order.getTotalPrice());

        return new PaymentInstructionsResponse(
            walletAddress,
            amountInEth,
            "Send exactly " + amountInEth + " ETH to the wallet address using Sepolia testnet"
        );
    }

    /**
     * User submits their transaction hash after sending payment
     */
    @PostMapping("/verify/{orderId}")
    public PaymentVerificationResponse verifyPayment(
            @PathVariable Long orderId,
            @RequestBody PaymentVerificationRequest request) {

        Order order = orderService.getOrder(orderId);
        BigDecimal expectedAmount = convertToEth(order.getTotalPrice());

        // Verify the payment
        boolean amountCorrect = paymentService.verifyPayment(
            request.getTransactionHash(),
            expectedAmount
        );

        boolean confirmed = paymentService.isTransactionConfirmed(
            request.getTransactionHash(),
            3  // Wait for 3 block confirmations
        );

        if (amountCorrect && confirmed) {
            orderService.confirmOrder(orderId);
            return new PaymentVerificationResponse(true, "Payment confirmed. Order complete.");
        } else if (amountCorrect && !confirmed) {
            return new PaymentVerificationResponse(false, "Payment found but waiting for confirmations.");
        } else {
            return new PaymentVerificationResponse(false, "Payment not found or incorrect amount.");
        }
    }

    private BigDecimal convertToEth(BigDecimal priceInYourCurrency) {
        // Simple conversion - adjust rate as needed for testing
        BigDecimal ethRate = new BigDecimal("0.001"); // 1 unit = 0.001 ETH
        return priceInYourCurrency.multiply(ethRate);
    }
}

// 1. User completes order → calls GET /api/payments/instructions/{orderId}
// 2. Your API returns: wallet address + exact amount to send
// 3. User opens MetaMask, sends that amount to your address on Sepolia
// 4. User copies the transaction hash from MetaMask
// 5. User calls POST /api/payments/verify/{orderId} with the transaction hash
// 6. Your service checks the blockchain, confirms payment, completes the order

// ? 1. Endpoint where you fund money and subtract from the user balance
// ? 2. The user directly pays for his order (but somehow has to send something to verify he pays for his order)
// ? Or we can have in a table userId -> address so we know who who is (even better)
