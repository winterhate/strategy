package cz.kb.oleg.strategy.decisions;

import cz.kb.oleg.strategy.api.Strategy;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import cz.kb.oleg.strategy.api.StrategyHandler;
import cz.kb.oleg.strategy.decisions.dto.DecisionDto;
import cz.kb.oleg.strategy.service.AsyncStrategyExecutor;
import cz.kb.oleg.strategy.service.TestableStrategyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DecisionConfiguration {

    @Bean
    public StrategyExecutor<DecisionDto> decisionExecutor() {
        return new AsyncStrategyExecutor<>((Exception e, Strategy<DecisionDto> strategy) -> {
            DecisionStrategy.log.info("Exception in executor {}: {}", strategy.getClass().getSimpleName(), e.getMessage());
        });
    }

    @Bean
    public StrategyHandler<DecisionDto> decisionStrategyHandler(StrategyExecutor<DecisionDto> decisionExecutor,
            List<DecisionStrategy<DecisionDto>> decisionStrategies) {
        return new TestableStrategyHandler<>(decisionExecutor, decisionStrategies);
    }

}
