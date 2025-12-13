package cz.kb.oleg.strategy;

import cz.kb.oleg.strategy.api.StrategyDispatcher;
import cz.kb.oleg.strategy.api.StrategyExecutor;
import cz.kb.oleg.strategy.api.StrategySelector;
import cz.kb.oleg.strategy.events.EventStrategy;
import cz.kb.oleg.strategy.events.dto.EventDto;
import cz.kb.oleg.strategy.events.dto.EventOneDto;
import cz.kb.oleg.strategy.events.dto.EventTwoDto;
import cz.kb.oleg.strategy.service.dispatcher.DefaultStrategyDispatcher;
import cz.kb.oleg.strategy.service.dispatcher.ValidatingStrategyDispatcher;
import cz.kb.oleg.strategy.service.executors.DefaultStrategyExecutor;
import cz.kb.oleg.strategy.service.selectors.CachedStrategySelector;
import jakarta.validation.ConstraintViolationException;
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
import java.util.Locale;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class StrategyApplicationTests {

    static {
        Locale.setDefault(Locale.US);
    }

    @Configuration
    @ComponentScan("cz.kb.oleg.strategy")
    static class EventConfig {

        @Bean
        public StrategySelector<EventDto, EventStrategy<EventDto>> eventDtoStrategySelector(List<EventStrategy<EventDto>> eventStrategies) {
            return new CachedStrategySelector<>(eventStrategies);
        }

        @Bean
        public StrategyExecutor<EventDto> eventDtoStrategyExecutor() {
            return new DefaultStrategyExecutor<>();
//            return new AsyncStrategyExecutor<>(new DefaultStrategyExecutor<>());
        }

        @Bean
        public StrategyDispatcher<EventDto> eventDtoStrategyHandler(
                StrategySelector<EventDto, EventStrategy<EventDto>> eventDtoStrategySelector,
                StrategyExecutor<EventDto> eventDtoStrategyExecutor,
                Validator validator) {
            return new ValidatingStrategyDispatcher<>(
                    new DefaultStrategyDispatcher<>(eventDtoStrategySelector, eventDtoStrategyExecutor),
                    validator
            );
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
        data.setDetailTwo(""); // to test validation
        final var constraintViolationException = assertThrows(ConstraintViolationException.class, () -> eventDtoStrategyDispatcher.dispatch(data));
        assertEquals("detailTwo: must not be blank", constraintViolationException.getMessage());
    }

}
