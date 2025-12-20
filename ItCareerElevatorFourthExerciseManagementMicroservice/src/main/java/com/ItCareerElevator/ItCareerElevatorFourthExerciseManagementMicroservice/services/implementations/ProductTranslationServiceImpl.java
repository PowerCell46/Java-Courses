package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.TranslationFieldRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.ProductTranslation;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.InvalidTranslationsException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories.ProductTranslationRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.LocaleService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.ProductTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductTranslationServiceImpl implements ProductTranslationService {

    private final LocaleService localeService;
    private final ProductTranslationRepository productTranslationRepository;

    @Override
    public Set<ProductTranslation> createTranslations(CreateProductRequestDTO requestDTO, Product product) {
        if (requestDTO.getNameTranslations().size() != requestDTO.getDescriptionTranslations().size()) {
            throw new InvalidTranslationsException("Name locales and description locales don't match in size.");
        }

        Set<ProductTranslation> productTranslations = new HashSet<>();
        for (int i = 0; i < requestDTO.getNameTranslations().size(); ++i) {
            if (!requestDTO.getNameTranslations().get(i).getCode().equals(requestDTO.getDescriptionTranslations().get(i).getCode())) {
                throw new InvalidTranslationsException("Non-matching order of locales between the translations.");
            }

            productTranslations.add(
                    constructNonPersistedProductTranslation(
                            product,
                            requestDTO.getNameTranslations().get(i),
                            requestDTO.getDescriptionTranslations().get(i)
                    )
            );
        }

        log.info("Persisting {} number of product translations to the database.", productTranslations.size());
        productTranslationRepository.saveAll(productTranslations);

        return productTranslations;
    }

    @Override
    public void updateTranslations(UpdateProductRequestDTO requestDto, Product product) {
        if (requestDto.getNameTranslations() != null) {
            for (TranslationFieldRequestDTO locale: requestDto.getNameTranslations()) {

                ProductTranslation previousTranslation = productTranslationRepository
                        .findByProductAndLocaleCodeAndIsDeletedIsFalse(product, locale.getCode())
                        .orElseThrow(() -> new InvalidTranslationsException(
                                String.format(
                                        "No such translation locale (%s) found for the updated product.",
                                        locale.getCode()
                                )
                        ));

                previousTranslation.setName(locale.getTranslation());
                save(previousTranslation);
            }
        }

        if (requestDto.getDescriptionTranslations() != null) {
            for (TranslationFieldRequestDTO locale: requestDto.getDescriptionTranslations()) {

                ProductTranslation previousTranslation = productTranslationRepository
                        .findByProductAndLocaleCodeAndIsDeletedIsFalse(product, locale.getCode())
                        .orElseThrow(() -> new InvalidTranslationsException(
                                String.format(
                                        "No such translation locale (%s) found for the updated product.",
                                        locale.getCode()
                                )
                        ));

                previousTranslation.setDescription(locale.getTranslation());
                save(previousTranslation);
            }
        }
    }

    @Override
    public ProductTranslation save(ProductTranslation productTranslation) {
        log.info("Persisting productTranslation to the database.");

        return productTranslationRepository.save(productTranslation);
    }

    private ProductTranslation constructNonPersistedProductTranslation(
            Product product, TranslationFieldRequestDTO nameDTO,
            TranslationFieldRequestDTO descriptionDTO
    ) {
        return new ProductTranslation(
                nameDTO.getTranslation(),
                descriptionDTO.getTranslation(),
                product,
                localeService.getByCode(nameDTO.getCode())
        );
    }
}
