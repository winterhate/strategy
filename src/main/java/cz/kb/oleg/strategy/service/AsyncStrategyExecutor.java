package cz.kb.oleg.strategy.service;

import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class AsyncStrategyExecutor<T> implements StrategyExecutor<T>, DisposableBean {

    private final ExecutorService executor;
    private final BiConsumer<Exception, Strategy<T>> exceptionHandler;

    public AsyncStrategyExecutor() {
        this(AsyncStrategyExecutor::defaultExceptionHandler, newCachedThreadPool());
    }

    public AsyncStrategyExecutor(ExecutorService executor) {
        this(AsyncStrategyExecutor::defaultExceptionHandler, executor);
    }

    public AsyncStrategyExecutor(BiConsumer<Exception, Strategy<T>> exceptionHandler) {
        this(exceptionHandler, newCachedThreadPool());
    }

    public AsyncStrategyExecutor(BiConsumer<Exception, Strategy<T>> exceptionHandler, ExecutorService executor) {
        this.exceptionHandler = exceptionHandler;
        this.executor = executor;
    }

    @Override
    public void executeStrategy(Strategy<T> strategy, T t) {
        executor.execute(() -> {
            try {
                strategy.handle(t);
            } catch (Exception e) {
                exceptionHandler.accept(e, strategy);
            }
        });
    }

    private static void defaultExceptionHandler(Exception e, Strategy<?> strategy) {
        log.error("Error executing strategy {}: {}", strategy.getClass().getSimpleName(), e.getMessage(), e);
    }

    @Override
    public void destroy() throws Exception {
        executor.shutdown();
        if (executor.awaitTermination(20, SECONDS)) {
            log.info("AsyncStrategyHandler executor shut down gracefully.");
        } else {
            log.warn("AsyncStrategyHandler executor did not shut down in the allocated time. Forcing shutdown.");
            executor.shutdownNow();
        };
    }
}
