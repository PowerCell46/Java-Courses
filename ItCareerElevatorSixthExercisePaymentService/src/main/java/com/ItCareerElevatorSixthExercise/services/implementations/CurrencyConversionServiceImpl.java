package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.services.interfaces.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    @Value("${ethereum-euro.ratio.url}")
    private String ETH_EUR_RATIO_ENDPOINT;

    private final RestTemplate restTemplate;

    @Override
    public BigDecimal convertEtherToEuro(BigDecimal ether) {
        return ether.multiply(getEthEurPrice());
    }

    public BigDecimal getEthEurPrice() {
        Map response = restTemplate.getForObject(ETH_EUR_RATIO_ENDPOINT, Map.class);
        Map ethereum = (Map) response.get("ethereum");
        return new BigDecimal(ethereum.get("eur").toString());
    }
}
