package cz.kb.oleg.strategy.decisions;

import cz.kb.oleg.strategy.api.StrategyDispatcher;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import cz.kb.oleg.strategy.api.StrategySelector;
import cz.kb.oleg.strategy.decisions.dto.DecisionDto;
import cz.kb.oleg.strategy.service.dispatcher.DefaultStrategyDispatcher;
import cz.kb.oleg.strategy.service.dispatcher.ValidatingStrategyDispatcher;
import cz.kb.oleg.strategy.service.executors.AsyncStrategyExecutor;
import cz.kb.oleg.strategy.service.executors.DefaultStrategyExecutor;
import cz.kb.oleg.strategy.service.result.NoValue;
import cz.kb.oleg.strategy.service.selectors.TestableStrategySelector;
import jakarta.validation.Validator;
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
    public StrategyExecutor<DecisionDto, NoValue> decisionStrategyExecutor() {
        return new AsyncStrategyExecutor<>(new DefaultStrategyExecutor<>());
    }

    @Bean
    public StrategyDispatcher<DecisionDto, NoValue> decisionStrategyHandler(
            StrategySelector<DecisionDto, DecisionStrategy<DecisionDto>> decisionStrategySelector,
            StrategyExecutor<DecisionDto, NoValue> decisionStrategyExecutor,
            Validator validator) {
        return new ValidatingStrategyDispatcher<>(new DefaultStrategyDispatcher<>(decisionStrategySelector, decisionStrategyExecutor), validator);
    }

}
