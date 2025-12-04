package cz.kb.oleg.strategy.api;

import java.util.List;

public interface StrategyDispatcher<T, R> {

    List<Result<R>> dispatch(T data);

}
