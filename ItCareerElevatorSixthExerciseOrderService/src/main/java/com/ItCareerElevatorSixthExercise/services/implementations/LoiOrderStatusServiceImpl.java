package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.entities.LoiOrderStatus;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchLoiOrderStatusException;
import com.ItCareerElevatorSixthExercise.repositories.LoiOrderStatusRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.LoiOrderStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoiOrderStatusServiceImpl implements LoiOrderStatusService {

    private final LoiOrderStatusRepository loiOrderStatusRepository;

    @Override
    public LoiOrderStatus getByListOptionItemCode(Long code) {
        return loiOrderStatusRepository
                .findByCode(code)
                .orElseThrow(() ->
                        new NoSuchLoiOrderStatusException(String.format("No LoiOrderStatus found with code %d.", code)));
    }
}
