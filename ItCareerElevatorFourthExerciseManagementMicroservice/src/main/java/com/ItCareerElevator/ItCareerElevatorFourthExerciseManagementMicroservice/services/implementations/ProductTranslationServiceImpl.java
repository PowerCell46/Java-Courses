package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.LocaleRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.ProductTranslation;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.InvalidLocalesException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories.ProductTranslationRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.LocaleService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.ProductTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductTranslationServiceImpl implements ProductTranslationService {

    private final LocaleService localeService;
    private final ProductTranslationRepository productTranslationRepository;

    @Override
    public Set<ProductTranslation> createTranslations(CreateProductRequestDTO requestDTO, Product product) {
        if (requestDTO.getNameLocales().size() != requestDTO.getDescriptionLocales().size()) {
            throw new InvalidLocalesException("Name locales and description locales don't match in size.");
        }

        Set<ProductTranslation> productTranslations = new HashSet<>();
        for (int i = 0; i < requestDTO.getNameLocales().size(); ++i) {
            if (!requestDTO.getNameLocales().get(i).getCode().equals(requestDTO.getDescriptionLocales().get(i).getCode())) {
                throw new InvalidLocalesException("Non-matching order of locales between the translation locales.");
            }

            productTranslations.add(
                    constructNonPersistedProductTranslation(
                            product,
                            requestDTO.getNameLocales().get(i),
                            requestDTO.getDescriptionLocales().get(i)
                    )
            );
        }

        log.info("Persisting {} number of product locales to the database.", productTranslations.size());
        productTranslationRepository.saveAll(productTranslations);

        return productTranslations;
    }

    @Override
    public void updateTranslations(UpdateProductRequestDTO requestDto, Product product) {
        if (requestDto.getNameLocales() != null) {
            for (LocaleRequestDTO locale: requestDto.getNameLocales()) {
                Optional<ProductTranslation> previousTranslation = productTranslationRepository
                        .findByProductAndLocaleCodeAndIsDeletedIsFalse(product, locale.getCode());

                if (previousTranslation.isEmpty()) {
                    throw new InvalidLocalesException(
                            String.format("No such locale %s found for the updated product translation.", locale.getCode())
                    );
                }

                previousTranslation.get().setName(locale.getTranslation());
                save(previousTranslation.get());
            }
        }

        if (requestDto.getDescriptionLocales() != null) {
            for (LocaleRequestDTO locale: requestDto.getDescriptionLocales()) {
                Optional<ProductTranslation> previousTranslation = productTranslationRepository
                        .findByProductAndLocaleCodeAndIsDeletedIsFalse(product, locale.getCode());

                if (previousTranslation.isEmpty()) {
                    throw new InvalidLocalesException(
                            String.format("No such locale %s found for the updated product translation.", locale.getCode())
                    );
                }

                previousTranslation.get().setDescription(locale.getTranslation());
                save(previousTranslation.get());
            }
        }
    }

    @Override
    public ProductTranslation save(ProductTranslation productTranslation) {
        log.info("Persisting productTranslation to the database.");

        return productTranslationRepository.save(productTranslation);
    }

    private ProductTranslation constructNonPersistedProductTranslation(
            Product product, LocaleRequestDTO nameDTO, LocaleRequestDTO descriptionDTO
    ) {
        return new ProductTranslation(
                product,
                localeService.getByCode(nameDTO.getCode()),
                nameDTO.getTranslation(),
                descriptionDTO.getTranslation()
        );
    }
}
