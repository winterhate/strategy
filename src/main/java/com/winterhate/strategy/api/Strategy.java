package com.winterhate.strategy.api;

public interface Strategy<T> {

    void apply(T t);

}
