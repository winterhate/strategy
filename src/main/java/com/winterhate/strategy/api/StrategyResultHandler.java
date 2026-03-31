package com.winterhate.strategy.api;

import lombok.NonNull;

public interface StrategyResultHandler<T> {

    void onSuccess(@NonNull Strategy<T> strategy, @NonNull T data);

    void onException(@NonNull Strategy<T> strategy, @NonNull T data, @NonNull Exception e);

}
