package cz.kb.oleg.strategy.service.executors;

import cz.kb.oleg.strategy.api.Strategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class AsyncStrategyExecutor<T, R> extends DefaultStrategyExecutor<T, R> implements DisposableBean {

    private final ExecutorService executor;

    public AsyncStrategyExecutor() {
        this(newCachedThreadPool());
    }

    public AsyncStrategyExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void executeStrategy(Strategy<T, R> strategy, T t) {
        executor.execute(() -> super.executeStrategy(strategy, t));
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
        ;
    }
}
