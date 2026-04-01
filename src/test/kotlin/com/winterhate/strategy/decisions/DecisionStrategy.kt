package com.winterhate.strategy.decisions

import com.winterhate.strategy.service.strategies.TestableStrategy

interface DecisionStrategy<DecisionDto> : TestableStrategy<DecisionDto>

