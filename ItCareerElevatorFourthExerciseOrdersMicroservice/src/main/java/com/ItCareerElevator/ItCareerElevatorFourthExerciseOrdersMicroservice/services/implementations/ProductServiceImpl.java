package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.exceptions.NoSuchProductException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories.ProductRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product getById(String id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchProductException(String.format("No product found with id %s.", id)));
    }
}
