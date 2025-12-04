package cz.kb.oleg.strategy.service.strategies;

import cz.kb.oleg.strategy.api.ClassMappedStrategy;
import org.springframework.validation.annotation.Validated;

@Validated
public abstract class AbstractClassMappedStrategy<T> implements ClassMappedStrategy<T> {

    public boolean isApplicable(T input) {
        return input != null && getApplicableClass().isAssignableFrom(input.getClass());
    }

}
