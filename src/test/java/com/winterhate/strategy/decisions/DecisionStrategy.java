package com.winterhate.strategy.decisions;

import com.winterhate.strategy.decisions.dto.DecisionDto;
import com.winterhate.strategy.service.strategies.TestableStrategy;


public abstract class DecisionStrategy<T extends DecisionDto> implements TestableStrategy<T> {
}
