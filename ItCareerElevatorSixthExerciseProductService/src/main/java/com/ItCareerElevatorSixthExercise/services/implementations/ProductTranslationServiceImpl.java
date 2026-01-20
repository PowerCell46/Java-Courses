package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.request.CreateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.TranslationFieldRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UpdateProductRequestDTO;
import com.ItCareerElevatorSixthExercise.entities.Product;
import com.ItCareerElevatorSixthExercise.entities.ProductTranslation;
import com.ItCareerElevatorSixthExercise.exceptions.InvalidTranslationsException;
import com.ItCareerElevatorSixthExercise.exceptions.product.ProductAlreadyExistsException;
import com.ItCareerElevatorSixthExercise.repositories.ProductTranslationRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.LocaleService;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProductTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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
            for (TranslationFieldRequestDTO localeRequestDTO: requestDto.getNameTranslations()) {

                ProductTranslation previousTranslation = productTranslationRepository
                        .findByProductAndLocaleCode(product, localeRequestDTO.getCode())
                        .orElseThrow(() -> new InvalidTranslationsException(
                                String.format(
                                        "No such translation locale (%s) found for the updated product.",
                                        localeRequestDTO.getCode()
                                )
                        ));

                previousTranslation.setName(localeRequestDTO.getTranslation());
                save(previousTranslation);
            }
        }

        if (requestDto.getDescriptionTranslations() != null) {
            for (TranslationFieldRequestDTO localeRequestDTO: requestDto.getDescriptionTranslations()) {

                ProductTranslation previousTranslation = productTranslationRepository
                        .findByProductAndLocaleCode(product, localeRequestDTO.getCode())
                        .orElseThrow(() -> new InvalidTranslationsException(
                                String.format(
                                        "No such translation locale (%s) found for the updated product.",
                                        localeRequestDTO.getCode()
                                )
                        ));

                previousTranslation.setDescription(localeRequestDTO.getTranslation());
                save(previousTranslation);
            }
        }
    }

    @Override
    public ProductTranslation save(ProductTranslation productTranslation) {
        log.info("Persisting productTranslation to the database.");

        return productTranslationRepository.save(productTranslation);
    }

    @Override
    public void validateTranslations(List<TranslationFieldRequestDTO> nameTranslations) {
        if (translatedNameAlreadyExists(nameTranslations)) {
            throw new ProductAlreadyExistsException(
                    "Cannot create product, because the name is already taken (in one or more language/s)."
            );
        }
    }

    private boolean translatedNameAlreadyExists(List<TranslationFieldRequestDTO> nameTranslations) {
        return nameTranslations
                .stream()
                .anyMatch(translationNameRequestDTO ->
                        !productTranslationRepository.findAllByName(translationNameRequestDTO.getTranslation())
                                .isEmpty()
                );
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
