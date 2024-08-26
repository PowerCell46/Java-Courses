package bg.softuni.jsonprocessingexercise.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidationUtilImpl implements ValidationUtil {
    private final Validator validator;

    @Override
    public <E> boolean isValid(E entity) {
        return this.getViolations(entity).isEmpty();
    }

    @Override
    public <E> Set<ConstraintViolation<E>> getViolations(E entity) {
        return this.validator.validate(entity);
    }
}
