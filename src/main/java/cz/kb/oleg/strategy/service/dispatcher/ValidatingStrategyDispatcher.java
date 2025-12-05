package cz.kb.oleg.strategy.service.dispatcher;

import cz.kb.oleg.strategy.api.Result;
import cz.kb.oleg.strategy.api.StrategyDispatcher;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static cz.kb.oleg.strategy.api.Result.Err;

@Slf4j
public class ValidatingStrategyDispatcher<T, R> implements StrategyDispatcher<T, R> {

    private final StrategyDispatcher<T, R> strategyDispatcherDelegate;
    private final Validator validator;

    public ValidatingStrategyDispatcher(StrategyDispatcher<T, R> strategyDispatcher, Validator validator) {
        this.strategyDispatcherDelegate = strategyDispatcher;
        this.validator = validator;
    }

    @Override
    public List<Result<R>> dispatch(T data) {
        var violations = validator.validate(data);
        if (!violations.isEmpty()) {
            return List.of(Err(new ConstraintViolationException(violations)));
        }
        return strategyDispatcherDelegate.dispatch(data);
    }

}
