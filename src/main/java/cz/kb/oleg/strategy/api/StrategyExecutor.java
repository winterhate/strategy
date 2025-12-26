package cz.kb.oleg.strategy.api;

import lombok.NonNull;

public interface StrategyExecutor<T> {

    void executeStrategy(@NonNull Strategy<T> strategy, @NonNull T t);

}
