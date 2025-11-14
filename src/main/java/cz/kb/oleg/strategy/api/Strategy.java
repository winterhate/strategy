package cz.kb.oleg.strategy.api;

public interface Strategy<T> {
    void handle(T t);
}
