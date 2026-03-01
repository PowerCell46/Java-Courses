package com.ItCareerElevatorSixthExercise.services.interfaces;


import com.ItCareerElevatorSixthExercise.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.TranslationFieldRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.entities.product.Product;
import com.ItCareerElevatorSixthExercise.entities.product.ProductTranslation;

import java.util.List;
import java.util.Set;

public interface ProductTranslationService {

    Set<ProductTranslation> create(CreateProductRequestDTO requestDTO, Product product);

    void update(UpdateProductRequestDTO requestDto, Product product);

    ProductTranslation save(ProductTranslation productTranslation);

    void validate(List<TranslationFieldRequestDTO> nameTranslations);
}
