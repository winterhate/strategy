package cz.kb.oleg.strategy.decisions;

import cz.kb.oleg.strategy.api.TestableStrategy;
import cz.kb.oleg.strategy.decisions.dto.DecisionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class DecisionStrategy<T extends DecisionDto> implements TestableStrategy<T> {
    public static final Logger log = LoggerFactory.getLogger(DecisionStrategy.class);
}
