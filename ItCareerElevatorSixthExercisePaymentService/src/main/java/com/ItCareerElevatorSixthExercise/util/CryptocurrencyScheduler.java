package com.ItCareerElevatorSixthExercise.util;

import com.ItCareerElevatorSixthExercise.entities.LastProcessedBlockNumber;
import com.ItCareerElevatorSixthExercise.repositories.LastProcessedBlockNumberRepository;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CryptocurrencyScheduler {

    @Value("${ethereum.wallet.address}")
    private String walletAddress;

    private final Web3j web3j;
    private final UserService userService;
    private final LastProcessedBlockNumberRepository blockNumberRepository;

    @SneakyThrows
    @Transactional
    @Scheduled(fixedDelay = 1_000 * 60 * 15) // TODO: 15 ATM, maybe should be more frequent
    public void processIncomingTransactions() {
        LastProcessedBlockNumber lastScannedBlockState = fetchCurrentBlockScanState();
        BigInteger currentBlockNumber = web3j.ethBlockNumber().send().getBlockNumber();

        // TODO: With the current logic, when we are initializing the entity, won't we skip the loop?
        for (
                BigInteger blockNum = lastScannedBlockState.getLastProcessedBlock().add(BigInteger.ONE);
                blockNum.compareTo(currentBlockNumber) <= 0;
                blockNum = blockNum.add(BigInteger.ONE)
        ) {
            EthBlock.Block block = web3j
                    .ethGetBlockByNumber(DefaultBlockParameter.valueOf(lastScannedBlockState.getLastProcessedBlock()), true)
                    .send()
                    .getBlock();

            block
                    .getTransactions()
                    .stream()
                    .map(transaction -> (EthBlock.TransactionObject) transaction)
                    .filter(transaction -> transaction.getTo() != null && transaction.getTo().equalsIgnoreCase(walletAddress))
                    .forEach(userService::processTransaction);
        }

        lastScannedBlockState.setLastProcessedBlock(currentBlockNumber);
        blockNumberRepository.save(lastScannedBlockState);
    }

    // * Imitating the characteristics of a runtime exception.
    @SneakyThrows // * Allows us to throw any checked exception without defining it explicitly in the method signature.
    private LastProcessedBlockNumber fetchCurrentBlockScanState() {
        List<LastProcessedBlockNumber> blockScanQueryResult = blockNumberRepository.findAll();

        if (blockScanQueryResult.isEmpty()) {
            BigInteger blockNumber = web3j
                    .ethBlockNumber()
                    .send()
                    .getBlockNumber();

            log.info("Initializing blockScanState with value {}.", blockNumber);
            return blockNumberRepository.save(new LastProcessedBlockNumber(blockNumber));
        }

        return blockScanQueryResult.getFirst();
    }
}
