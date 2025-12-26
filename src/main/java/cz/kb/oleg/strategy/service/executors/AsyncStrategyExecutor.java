package cz.kb.oleg.strategy.service.executors;

import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class AsyncStrategyExecutor<T> extends DefaultStrategyExecutor<T> implements DisposableBean {

    private final StrategyExecutor<T> strategyExecutorDelegate;
    private final ExecutorService executor;

    public AsyncStrategyExecutor(@NonNull StrategyExecutor<T> strategyExecutorDelegate) {
        this(strategyExecutorDelegate, newCachedThreadPool());
    }

    public AsyncStrategyExecutor(@NonNull StrategyExecutor<T> strategyExecutorDelegate, @NonNull ExecutorService executor) {
        this.strategyExecutorDelegate = strategyExecutorDelegate;
        this.executor = executor;
    }

    @Override
    public void executeStrategy(@NonNull Strategy<T> strategy, @NonNull T t) {
        executor.submit(() -> strategyExecutorDelegate.executeStrategy(strategy, t));
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
