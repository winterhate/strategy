package cz.kb.oleg.strategy.service.dispatcher;

import cz.kb.oleg.strategy.api.StrategyDispatcher;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidatingStrategyDispatcher<T> implements StrategyDispatcher<T> {

    private final StrategyDispatcher<T> strategyDispatcherDelegate;
    private final Validator validator;

    public ValidatingStrategyDispatcher(StrategyDispatcher<T> strategyDispatcher, Validator validator) {
        this.strategyDispatcherDelegate = strategyDispatcher;
        this.validator = validator;
    }

    @Override
    public void dispatch(@NonNull T data) {
        var violations = validator.validate(data);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        strategyDispatcherDelegate.dispatch(data);
    }

}
