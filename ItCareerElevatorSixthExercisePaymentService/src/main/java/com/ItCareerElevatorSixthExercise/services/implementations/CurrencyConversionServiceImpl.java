package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.services.interfaces.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    @Value("${ethereum-euro.ratio.url}")
    private String ethEurRatioEndpoint;

    private final RestTemplate restTemplate;

    @Override
    public BigDecimal convertEtherToEuro(BigDecimal ether) {
        return ether.multiply(getEthEurPrice()).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getEthEurPrice() {
        Map<String, Map<String, Object>> response = restTemplate.getForObject(ethEurRatioEndpoint, Map.class);

        return Optional
                .ofNullable(response)
                .map(r -> r.get("ethereum"))
                .map(eth -> eth.get("eur"))
                .map(price -> new BigDecimal(price.toString()))
                .orElseThrow(() -> new IllegalStateException("Failed to fetch ETH/EUR price"));
    }
}
