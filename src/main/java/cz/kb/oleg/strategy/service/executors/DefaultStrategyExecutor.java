package cz.kb.oleg.strategy.service.executors;

import cz.kb.oleg.strategy.api.Result;
import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultStrategyExecutor<T, R> implements StrategyExecutor<T, R> {

    @Override
    public Result<R> executeStrategy(Strategy<T, R> strategy, T t) {
        try {
            final var result = strategy.apply(t);
            resultHandler(strategy, result);
            return result;
        } catch (Exception e) {
            resultHandler(strategy, Result.Err(e));
            return Result.Err(e);
        }
    }

    protected void resultHandler(Strategy<T, R> strategy, Result<R> result) {
        final var strategyName = strategy.getClass().getSimpleName();
        if (result.isOk()) {
            log.info("Strategy {} executed successfully with result: {}", strategyName, result.get());
        } else {
            log.error("Strategy {} execution failed with error: {}", strategyName, result.getErrorMessage());
        }
    }

}
