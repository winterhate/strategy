package cz.kb.oleg.strategy.decisions;

import cz.kb.oleg.strategy.api.Result;
import cz.kb.oleg.strategy.api.StrategyDispatcher;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import cz.kb.oleg.strategy.api.StrategySelector;
import cz.kb.oleg.strategy.decisions.dto.DecisionDto;
import cz.kb.oleg.strategy.service.DefaultStrategyDispatcher;
import cz.kb.oleg.strategy.service.executors.AsyncStrategyExecutor;
import cz.kb.oleg.strategy.service.selectors.TestableStrategySelector;
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
    public StrategyExecutor<DecisionDto, Result.Void> decisionStrategyExecutor() {
        return new AsyncStrategyExecutor<>();
    }

    @Bean
    public StrategyDispatcher<DecisionDto, Result.Void> decisionStrategyHandler(
            StrategySelector<DecisionDto, DecisionStrategy<DecisionDto>> decisionStrategySelector,
            StrategyExecutor<DecisionDto, Result.Void> decisionStrategyExecutor) {
        return new DefaultStrategyDispatcher<>(decisionStrategySelector, decisionStrategyExecutor);
    }

}
