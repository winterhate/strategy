package cz.kb.oleg.strategy.decisions;

import cz.kb.oleg.strategy.decisions.dto.DecisionDto;
import cz.kb.oleg.strategy.service.strategies.TestableStrategy;


public abstract class DecisionStrategy<T extends DecisionDto> implements TestableStrategy<T> {
}
