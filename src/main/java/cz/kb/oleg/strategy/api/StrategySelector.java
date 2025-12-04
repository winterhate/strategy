package cz.kb.oleg.strategy.api;

import java.util.List;

public interface StrategySelector<T, S extends Strategy<T, ?>> {

    List<S> selectStrategies(T t);

}
