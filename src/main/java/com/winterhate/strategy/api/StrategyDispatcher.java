package com.winterhate.strategy.api;

import lombok.NonNull;

public interface StrategyDispatcher<T> {

    void dispatch(@NonNull T data);

}
