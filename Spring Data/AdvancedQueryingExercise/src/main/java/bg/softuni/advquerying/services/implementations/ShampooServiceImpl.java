package bg.softuni.advquerying.services.implementations;

import bg.softuni.advquerying.entities.Label;
import bg.softuni.advquerying.entities.Shampoo;
import bg.softuni.advquerying.entities.Size;
import bg.softuni.advquerying.repositories.LabelRepository;
import bg.softuni.advquerying.repositories.ShampooRepository;
import bg.softuni.advquerying.services.interfaces.ShampooServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ShampooServiceImpl implements ShampooServiceI {
    private final ShampooRepository shampooRepository;
    private final LabelRepository labelRepository;


    @Override
    public Set<Shampoo> findAllShampoosBySizeOrderedById(Size size) {
        return this.shampooRepository.findAllBySizeOrderById(size);
    }

    @Override
    public Set<Shampoo> findAllShampoosByLabelOrLabelId(Size size, long labelId) {
        Optional<Label> optionalLabel = labelRepository.findById(labelId);

        if (optionalLabel.isPresent()) {
            Label label = optionalLabel.get();
            return shampooRepository.findAllBySizeOrLabelOrderByPrice(size, label);
        }

        return null;
    }

    @Override
    public Set<Shampoo> findAllShampoosByPriceGreaterThan(double price) {
        return shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal.valueOf(price));
    }

    @Override
    public Integer getNumberOfShampoosCheaperThan(double price) {
        return shampooRepository.countByPriceIsLessThan(BigDecimal.valueOf(price));
    }

    @Override
    public Set<Shampoo> findAllByCertainIngredientNames(Collection<String> ingredientNames) {
        Set<Shampoo> shampoos = shampooRepository.findAllByIngredientsIn(ingredientNames);

        Map<String, Shampoo> uniqueShampoos = new LinkedHashMap<>();

        for (Shampoo shampoo : shampoos) {
            if (uniqueShampoos.containsKey(shampoo.getBrand())) {
                continue;
            }
            uniqueShampoos.put(shampoo.getBrand(), shampoo);
        }

        return new HashSet<>(uniqueShampoos.values());
    }

    @Override
    public Set<Shampoo> findAllByNumberOfIngredientsLowerThan(Integer numberOfIngredients) {
        return shampooRepository.findAllByNumberOfIngredientsLowerThan(numberOfIngredients);
    }
}
