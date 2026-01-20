package com.ItCareerElevatorSixthExercise.services.interfaces;


import com.ItCareerElevatorSixthExercise.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.TranslationFieldRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.entities.Product;
import com.ItCareerElevatorSixthExercise.entities.ProductTranslation;

import java.util.List;
import java.util.Set;

public interface ProductTranslationService {

    Set<ProductTranslation> createTranslations(CreateProductRequestDTO requestDTO, Product product);

    void updateTranslations(UpdateProductRequestDTO requestDto, Product product);

    ProductTranslation save(ProductTranslation productTranslation);

    void validateTranslations(List<TranslationFieldRequestDTO> nameTranslations);
}
