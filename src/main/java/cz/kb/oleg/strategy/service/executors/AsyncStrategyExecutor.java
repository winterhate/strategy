package cz.kb.oleg.strategy.service.executors;

import cz.kb.oleg.strategy.api.Result;
import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class AsyncStrategyExecutor<T, R> extends DefaultStrategyExecutor<T, R> implements DisposableBean {

    private final StrategyExecutor<T, R> strategyExecutorDelegate;
    private final ExecutorService executor;

    public AsyncStrategyExecutor(StrategyExecutor<T, R> strategyExecutorDelegate) {
        this(strategyExecutorDelegate, newCachedThreadPool());
    }

    public AsyncStrategyExecutor(StrategyExecutor<T, R> strategyExecutorDelegate, ExecutorService executor) {
        this.strategyExecutorDelegate = Objects.requireNonNull(strategyExecutorDelegate);
        this.executor = Objects.requireNonNull(executor);
    }

    @Override
    public Result<R> executeStrategy(Strategy<T, R> strategy, T t) {
        final var resultFuture = executor.submit(() -> strategyExecutorDelegate.executeStrategy(strategy, t));
        return Result.Future(resultFuture);
    }

    @Override
    public void destroy() throws Exception {
        executor.shutdown();
        if (executor.awaitTermination(20, SECONDS)) {
            log.info("AsyncStrategyHandler executor shut down gracefully.");
        } else {
            log.warn("AsyncStrategyHandler executor did not shut down in the allocated time. Forcing shutdown.");
            executor.shutdownNow();
        }
    }

}
