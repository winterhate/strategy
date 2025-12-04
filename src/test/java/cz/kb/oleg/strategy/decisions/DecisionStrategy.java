package cz.kb.oleg.strategy.decisions;

import cz.kb.oleg.strategy.api.Result;
import cz.kb.oleg.strategy.api.TestableStrategy;
import cz.kb.oleg.strategy.decisions.dto.DecisionDto;


public abstract class DecisionStrategy<T extends DecisionDto> implements TestableStrategy<T, Result.Void> {
}
