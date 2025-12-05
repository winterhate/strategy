package cz.kb.oleg.strategy.service.result;

/**
 * Represents an empty result, i.e. a result that contains no value.
 */
public class NoValue {
    private static final NoValue INSTANCE = new NoValue();

    private NoValue() {
    }

    public static NoValue getInstance() {
        return INSTANCE;
    }
}
