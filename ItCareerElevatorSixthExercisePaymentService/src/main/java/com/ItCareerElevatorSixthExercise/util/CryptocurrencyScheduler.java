package com.ItCareerElevatorSixthExercise.util;

import com.ItCareerElevatorSixthExercise.entities.BlockScanState;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.repositories.BlockScanStateRepository;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final UserRepository userRepository;
    private final BlockScanStateRepository blockScanStateRepository;

    @SneakyThrows
    @Transactional
    @Scheduled(fixedDelay = 1_000 * 60 * 15) // TODO: 15 ATM, maybe should be more frequent
    public void func() { // TODO: Change name
        BlockScanState lastScannedBlockState = fetchCurrentBlockScanState();
        BigInteger currentBlockNumber = web3j.ethBlockNumber().send().getBlockNumber();

        // Not sure if correct
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
                    .map(tx -> (EthBlock.TransactionObject) tx)
                    .filter(tx -> tx.getTo() != null && tx.getTo().equalsIgnoreCase(walletAddress))
                    .map(tx -> userRepository.findByWalletAddress(tx.getFrom()))
                    .filter(Optional::isPresent)
//                    .forEach() // TODO: Verify the amount
                    ;
        }

        lastScannedBlockState.setLastProcessedBlock(currentBlockNumber);
        blockScanStateRepository.save(lastScannedBlockState);
    }

    @SneakyThrows // * Allows us to throw any checked exception without defining it explicitly in the method signature.
    // * Imitating the characteristics of a runtime exception.
    private BlockScanState fetchCurrentBlockScanState() {
        List<BlockScanState> blockScanQueryResult = blockScanStateRepository.findAll();

        if (blockScanQueryResult.isEmpty()) {
            BigInteger blockNumber = web3j
                    .ethBlockNumber()
                    .send()
                    .getBlockNumber();

            log.info("Initializing blockScanState with value {}.", blockNumber);
            return blockScanStateRepository.save(new BlockScanState(blockNumber));
        }

        return blockScanQueryResult.getFirst();
    }
}
