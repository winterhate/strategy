package cz.kb.oleg.strategy.decisions;

import cz.kb.oleg.strategy.api.*;
import cz.kb.oleg.strategy.decisions.dto.DecisionDto;
import cz.kb.oleg.strategy.service.dispatcher.DefaultStrategyDispatcher;
import cz.kb.oleg.strategy.service.dispatcher.ValidatingStrategyDispatcher;
import cz.kb.oleg.strategy.service.executors.AsyncStrategyExecutor;
import cz.kb.oleg.strategy.service.executors.DefaultStrategyExecutor;
import cz.kb.oleg.strategy.service.executors.DefaultStrategyResultHandler;
import cz.kb.oleg.strategy.service.executors.LoggingStrategyExecutor;
import cz.kb.oleg.strategy.service.selectors.TestableStrategySelector;
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
