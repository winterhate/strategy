package cz.kb.oleg.strategy.api;

public interface ClassMappedStrategy<T, R> extends TestableStrategy<T, R> {

    Class<? extends T> getApplicableClass();

}
