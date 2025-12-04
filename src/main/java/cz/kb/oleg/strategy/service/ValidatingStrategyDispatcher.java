package cz.kb.oleg.strategy.service;

import cz.kb.oleg.strategy.api.Result;
import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import cz.kb.oleg.strategy.api.StrategySelector;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Slf4j
public class ValidatingStrategyDispatcher<T, R, S extends Strategy<T, R>> extends DefaultStrategyDispatcher<T, R, S> {

    private final Validator validator;

    public ValidatingStrategyDispatcher(StrategySelector<T, S> strategySelector, Validator validator) {
        super(strategySelector);
        this.validator = validator;
    }

    public ValidatingStrategyDispatcher(StrategySelector<T, S> strategySelector, StrategyExecutor<T, R> strategyExecutor, Validator validator) {
        super(strategySelector, strategyExecutor);
        this.validator = requireNonNull(validator);
    }

    @Override
    public List<Result<R>> dispatch(T data) {
        var violations = validator.validate(data);
        if (!violations.isEmpty()) {
            onValidationViolation(violations);
            return List.of();
        }
        return super.dispatch(data);
    }

    protected void onValidationViolation(Set<ConstraintViolation<T>> violations) {
        final var sb = formatViolations(violations);
        log.error("Validation failed:{}", sb);
    }

    protected String formatViolations(Set<ConstraintViolation<T>> violations) {
        StringBuilder sb = new StringBuilder();
        violations.forEach(violation -> sb.append(" ").append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append(";"));
        return sb.toString();
    }

}
