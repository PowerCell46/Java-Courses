package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.services.interfaces.PaymentService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

@Slf4j
@Service
public class CryptoPaymentServiceImpl implements PaymentService {

    @Value("${ethereum.node.url}")
    private String nodeUrl;

    @Value("${ethereum.wallet.address}")
    private String walletAddress;

    private Web3j web3j;

    @PostConstruct
    public void init() {
        web3j = Web3j.build(new HttpService(nodeUrl));
    }

    public boolean verifyPayment(String transactionHash, BigDecimal expectedAmountInEth) {
        try {
            EthTransaction ethTransaction = web3j
                    .ethGetTransactionByHash(transactionHash)
                    .send();

            Transaction transaction = ethTransaction.getTransaction().orElse(null);

            if (transaction == null)
                return false;

            if (!transaction.getTo().equalsIgnoreCase(walletAddress))
                return false; // wrong recipient

            BigDecimal amountInWei = new BigDecimal(transaction.getValue());
            BigDecimal amountInEth = Convert.fromWei(amountInWei, Convert.Unit.ETHER);

            return amountInEth.compareTo(expectedAmountInEth) >= 0;

        } catch (Exception ex) {
            throw new RuntimeException("FIX...");
        }
    }

    public boolean isTransactionConfirmed(String transactionHash, int requiredConfirmations) {
        try {
            EthTransaction ethTransaction = web3j
                    .ethGetTransactionByHash(transactionHash)
                    .send();

            Transaction transaction = ethTransaction.getTransaction().orElse(null);

            if (transaction == null || transaction.getBlockNumber() == null)
                return false;

            BigInteger transactionBlockNumber = transaction.getBlockNumber();
            BigInteger currentBlockNumber = web3j.ethBlockNumber().send().getBlockNumber();

            int confirmations = currentBlockNumber.subtract(transactionBlockNumber).intValue();
            return confirmations >= requiredConfirmations;

        } catch (Exception ex) {
            throw new RuntimeException("FIX...");
        }
    }
}
