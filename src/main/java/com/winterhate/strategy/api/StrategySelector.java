package com.winterhate.strategy.api;

import lombok.NonNull;

import java.util.List;

public interface StrategySelector<T, S extends Strategy<T>> {

    List<S> selectStrategies(@NonNull T t);

}
