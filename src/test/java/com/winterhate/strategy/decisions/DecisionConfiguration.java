package com.winterhate.strategy.decisions;

import com.winterhate.strategy.api.*;
import com.winterhate.strategy.decisions.dto.DecisionDto;
import com.winterhate.strategy.service.dispatcher.DefaultStrategyDispatcher;
import com.winterhate.strategy.service.dispatcher.ValidatingStrategyDispatcher;
import com.winterhate.strategy.service.executors.AsyncStrategyExecutor;
import com.winterhate.strategy.service.executors.DefaultStrategyExecutor;
import com.winterhate.strategy.service.executors.DefaultStrategyResultHandler;
import com.winterhate.strategy.service.executors.LoggingStrategyExecutor;
import com.winterhate.strategy.service.selectors.TestableStrategySelector;
import jakarta.validation.Validator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class DecisionConfiguration {

    @Bean
    public StrategySelector<DecisionDto, DecisionStrategy<DecisionDto>> decisionStrategySelector(List<DecisionStrategy<DecisionDto>> strategies) {
        return new TestableStrategySelector<>(strategies);
    }

    @Bean
    public StrategyResultHandler<DecisionDto> decisionStrategyExecutorExceptionHandler() {
        return new DefaultStrategyResultHandler<>() {
            @Override
            public void onException(@NonNull Strategy<DecisionDto> strategy, @NonNull DecisionDto decisionDto, @NonNull Exception e) {
                final var strategyName = getStrategyName(strategy);
                log.warn("Strategy {} execution failed with error: {}", strategyName, e.getMessage());
            }
        };
    }

    @Bean
    public StrategyExecutor<DecisionDto> decisionStrategyExecutor(StrategyResultHandler<DecisionDto> decisionStrategyResultHandler) {
        return new AsyncStrategyExecutor<>(new LoggingStrategyExecutor<>(new DefaultStrategyExecutor<>(decisionStrategyResultHandler)));
    }

    @Bean
    public StrategyDispatcher<DecisionDto> decisionStrategyHandler(
            StrategySelector<DecisionDto, DecisionStrategy<DecisionDto>> decisionStrategySelector,
            StrategyExecutor<DecisionDto> decisionStrategyExecutor,
            Validator validator) {
        return new ValidatingStrategyDispatcher<>(new DefaultStrategyDispatcher<>(decisionStrategySelector, decisionStrategyExecutor), validator);
    }

}
