package cz.kb.oleg.strategy.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface StrategyDispatcher<T, R> {

    List<Result<R>> dispatch(T data);

    default List<List<Result<R>>> dispatchBatch(T... dataBatch) {
        return Arrays.stream(dataBatch)
                .map(this::dispatch)
                .toList();
    }

    default List<List<Result<R>>> dispatchBatch(Collection<T> dataBatch) {
        return dataBatch.stream()
                .map(this::dispatch)
                .toList();
    }

}
