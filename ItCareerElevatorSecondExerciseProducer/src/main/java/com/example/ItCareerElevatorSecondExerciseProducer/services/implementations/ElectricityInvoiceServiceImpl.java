package com.example.ItCareerElevatorSecondExerciseProducer.services.implementations;

import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.CreateElectricityInvoiceRequestDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.ElectricityInvoiceResponseDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.entities.ElectricityInvoice;
import com.example.ItCareerElevatorSecondExerciseProducer.entities.LoiDocumentType;
import com.example.ItCareerElevatorSecondExerciseProducer.entities.LoiMeasurementUnit;
import com.example.ItCareerElevatorSecondExerciseProducer.exceptions.InvalidRelationshipException;
import com.example.ItCareerElevatorSecondExerciseProducer.repositories.ElectricityInvoiceRepository;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.ElectricityInvoiceProducerService;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.ElectricityInvoiceService;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.LoiDocumentTypeService;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.LoiMeasurementUnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElectricityInvoiceServiceImpl implements ElectricityInvoiceService {

    private final ElectricityInvoiceRepository electricityInvoiceRepository;

    private final ElectricityInvoiceProducerService electricityInvoiceProducerService;
    private final LoiDocumentTypeService loiDocumentTypeService;
    private final LoiMeasurementUnitService loiMeasurementUnitService;

    private static final BigDecimal VAT_VALUE = BigDecimal.valueOf(0.2);

    @Override
    public ElectricityInvoiceResponseDTO processElectricityInvoice(CreateElectricityInvoiceRequestDTO requestDTO) {
        validateRelationships(requestDTO);

        ElectricityInvoice electricityInvoice = constructNonPersistedElectricityInvoice(requestDTO);

        electricityInvoice = save(electricityInvoice); // ? Do I need to do that reassignment

        // TODO: Kafka produce message here...
        electricityInvoiceProducerService.send(electricityInvoice);
        // (make a method responsible for that logic) log message

        return constructElectricityInvoiceResponseDTO(electricityInvoice);
    }

    private void validateRelationships(CreateElectricityInvoiceRequestDTO requestDTO) {
        if (loiDocumentTypeService.getByListOptionItemCode(requestDTO.getLoiDocumentTypeCode()).isEmpty()) {
            throw new InvalidRelationshipException(String.format("Invalid LoiDocumentTypeCode: %s.", requestDTO.getLoiDocumentTypeCode()));
        }

        if (loiMeasurementUnitService.getByListOptionItemCode(requestDTO.getLoiMeasurementUnitCode()).isEmpty()) {
            throw new InvalidRelationshipException(String.format("Invalid LoiMeasurementUnitCode: %s.", requestDTO.getLoiMeasurementUnitCode()));
        }
    }

    private ElectricityInvoice constructNonPersistedElectricityInvoice(CreateElectricityInvoiceRequestDTO requestDTO) {
        BiFunction<BigDecimal, BigDecimal, BigDecimal> calculateTotalSumWithoutVAT = BigDecimal::multiply;
        BiFunction<BigDecimal, BigDecimal, BigDecimal> calculateVAT = BigDecimal::multiply;

        BigDecimal totalSumWithoutVAT = calculateTotalSumWithoutVAT
                .apply(requestDTO.getSinglePrice(), requestDTO.getQuantity());

        BigDecimal VAT = calculateVAT.apply(totalSumWithoutVAT, VAT_VALUE);

        LoiDocumentType loiDocumentType = loiDocumentTypeService
                .getByListOptionItemCode(requestDTO.getLoiDocumentTypeCode()).get();

        LoiMeasurementUnit loiMeasurementUnit = loiMeasurementUnitService
                .getByListOptionItemCode(requestDTO.getLoiMeasurementUnitCode()).get();

        return ElectricityInvoice
                .builder()
                .accessPoint(requestDTO.getAccessPoint())
                .invoiceNumber(generateInvoiceNumber())
                .iban(requestDTO.getIban())
                .periodFrom(requestDTO.getPeriodFrom())
                .periodTo(requestDTO.getPeriodTo())
                .quantity(requestDTO.getQuantity())
                .singlePrice(requestDTO.getSinglePrice())
                .totalSumWithoutVAT(totalSumWithoutVAT)
                .VAT(VAT)
                .loiDocumentType(loiDocumentType)
                .loiMeasurementUnit(loiMeasurementUnit)
                .build();
    }

    private ElectricityInvoiceResponseDTO constructElectricityInvoiceResponseDTO(ElectricityInvoice electricityInvoice) {
        return ElectricityInvoiceResponseDTO
                .builder()
                .accessPoint(electricityInvoice.getAccessPoint())
                .invoiceNumber(electricityInvoice.getInvoiceNumber())
                .iban(electricityInvoice.getIban())
                .periodFrom(electricityInvoice.getPeriodFrom())
                .periodTo(electricityInvoice.getPeriodTo())
                .quantity(electricityInvoice.getQuantity())
                .singlePrice(electricityInvoice.getSinglePrice())
                .totalSumWithoutVAT(electricityInvoice.getTotalSumWithoutVAT())
                .VAT(electricityInvoice.getVAT())
                .loiDocumentTypeName(electricityInvoice.getLoiDocumentType().getListOptionItemName())
                .loiMeasurementUnitName(electricityInvoice.getLoiMeasurementUnit().getListOptionItemName())
                .build();
    }

    @Override
    public ElectricityInvoice save(ElectricityInvoice electricityInvoice) {
        log.info("===> Saving ElectricityInvoice with access point: {} to the Database", electricityInvoice.getAccessPoint());

        return electricityInvoiceRepository.save(electricityInvoice);
    }

    public static String generateInvoiceNumber() {
        StringBuilder sb = new StringBuilder(10);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }
}
