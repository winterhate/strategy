package cz.kb.oleg.strategy.service.executors;

import cz.kb.oleg.strategy.api.Result;
import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultStrategyExecutor<T, R> implements StrategyExecutor<T, R> {

    @Override
    public void executeStrategy(Strategy<T, R> strategy, T t) {
        try {
            final var result = strategy.apply(t);
            resultHandler(result);
        } catch (Exception e) {
            resultHandler(Result.Err(e));
        }
    }

    protected void resultHandler(Result<R> result) {
        if (result.isOk()) {
            log.info("Strategy executed successfully with result: {}", result.get());
        } else {
            log.error("Strategy execution failed with error: {}", result.getErrorMessage());
        }
    }

}
