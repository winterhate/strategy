package cz.kb.oleg.strategy;

import cz.kb.oleg.strategy.api.Result;
import cz.kb.oleg.strategy.api.StrategyDispatcher;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import cz.kb.oleg.strategy.api.StrategySelector;
import cz.kb.oleg.strategy.events.EventStrategy;
import cz.kb.oleg.strategy.events.dto.EventDto;
import cz.kb.oleg.strategy.events.dto.EventOneDto;
import cz.kb.oleg.strategy.events.dto.EventTwoDto;
import cz.kb.oleg.strategy.service.ValidatingStrategyDispatcher;
import cz.kb.oleg.strategy.service.executors.DefaultStrategyExecutor;
import cz.kb.oleg.strategy.service.selectors.CachedStrategySelector;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

@SpringBootTest
@Slf4j
class StrategyApplicationTests {

    @Configuration
    @ComponentScan("cz.kb.oleg.strategy")
    static class EventConfig {

        @Bean
        public StrategySelector<EventDto, EventStrategy<EventDto>> eventDtoStrategySelector(List<EventStrategy<EventDto>> eventStrategies) {
            return new CachedStrategySelector<>(eventStrategies);
        }

        @Bean
        public StrategyExecutor<EventDto, Result.Void> eventDtoStrategyExecutor() {
            return new DefaultStrategyExecutor<>();
        }

        @Bean
        public StrategyDispatcher<EventDto> eventDtoStrategyHandler(
                StrategySelector<EventDto, EventStrategy<EventDto>> eventDtoStrategySelector,
                StrategyExecutor<EventDto, Result.Void> eventDtoStrategyExecutor,
                Validator validator) {
            return new ValidatingStrategyDispatcher<>(eventDtoStrategySelector, eventDtoStrategyExecutor, validator);
        }

        @Bean
        @ConditionalOnMissingBean
        public Validator validator() {
            try (var factory = buildDefaultValidatorFactory()) {
                return factory.getValidator();
            }
        }

    }

    @Autowired
    StrategyDispatcher<EventDto> eventDtoStrategyDispatcher;

    @Test
    void testStrategy() {
        eventDtoStrategyDispatcher.dispatch(new EventOneDto());
        final var data = new EventTwoDto();

        data.setDetailTwo(null); // to test validation
        eventDtoStrategyDispatcher.dispatch(data);
    }

}
