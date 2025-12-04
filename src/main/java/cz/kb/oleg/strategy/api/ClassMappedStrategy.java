package cz.kb.oleg.strategy.api;

public interface ClassMappedStrategy<T> extends TestableStrategy<T> {

    Class<? extends T> getApplicableClass();

}
