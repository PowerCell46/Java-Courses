package com.ItCareerElevatorSixthExercise.services.interfaces;

import java.math.BigDecimal;

public interface PaymentService {

    boolean verifyPayment(String transactionHash, BigDecimal expectedAmountInEth);

    boolean isTransactionConfirmed(String transactionHash, int requiredConfirmations);
}
