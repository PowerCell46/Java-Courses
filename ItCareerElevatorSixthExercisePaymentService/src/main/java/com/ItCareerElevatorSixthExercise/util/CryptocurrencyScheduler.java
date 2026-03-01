package com.ItCareerElevatorSixthExercise.util;

import com.ItCareerElevatorSixthExercise.entities.LastProcessedBlockNumber;
import com.ItCareerElevatorSixthExercise.repositories.LastProcessedBlockNumberRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserCryptoWalletService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.util.List;

@Slf4j
// @Component
@RequiredArgsConstructor
public class CryptocurrencyScheduler {

    @Value("${ethereum.wallet.address}")
    private String walletAddress;

    private final Web3j web3j;
    private final UserCryptoWalletService userCryptoWalletService;
    private final LastProcessedBlockNumberRepository blockNumberRepository;

    @SneakyThrows
    @Transactional
    // @Scheduled(fixedDelay = 1_000 * 60 * 5) // 5 minutes
    public void processIncomingTransactions() {
        LastProcessedBlockNumber lastScannedBlockState = fetchCurrentBlockScanState();
        BigInteger currentBlockNumber = web3j.ethBlockNumber().send().getBlockNumber();

        for (
                BigInteger blockNum = lastScannedBlockState.getLastProcessedBlock().add(BigInteger.ONE);
                blockNum.compareTo(currentBlockNumber) <= 0;
                blockNum = blockNum.add(BigInteger.ONE)
        ) {
            EthBlock.Block block = web3j
                    .ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNum), true)
                    .send()
                    .getBlock();

            block
                    .getTransactions()
                    .stream()
                    .map(transaction -> (EthBlock.TransactionObject) transaction)
                    .filter(this::isReceiverCorrect)
                    .forEach(userCryptoWalletService::processTransaction);
        }

        lastScannedBlockState.setLastProcessedBlock(currentBlockNumber);
        blockNumberRepository.save(lastScannedBlockState);
    }

    private boolean isReceiverCorrect(EthBlock.TransactionObject transactionObject) {
        return transactionObject != null && transactionObject.getTo().equalsIgnoreCase(walletAddress);
    }

    // * Imitating the characteristics of a runtime exception.
    @SneakyThrows // * Allows us to throw any checked exception without defining it explicitly in the method signature.
    private LastProcessedBlockNumber fetchCurrentBlockScanState() {
        List<LastProcessedBlockNumber> blockScanQueryResult = blockNumberRepository.findAll();

        if (blockScanQueryResult.isEmpty()) {
            BigInteger blockNumber = web3j
                    .ethBlockNumber()
                    .send()
                    .getBlockNumber()
                    .subtract(BigInteger.ONE);

            log.info("Initializing blockScanState with value {}.", blockNumber);
            return blockNumberRepository.save(new LastProcessedBlockNumber(blockNumber));
        }

        return blockScanQueryResult.getFirst();
    }
}
