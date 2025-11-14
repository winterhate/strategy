package cz.kb.oleg.strategy;

import cz.kb.oleg.strategy.api.StrategyHandler;
import cz.kb.oleg.strategy.decisions.TestableStrategyHandler;
import cz.kb.oleg.strategy.events.EventStrategy;
import cz.kb.oleg.strategy.events.dto.EventDto;
import cz.kb.oleg.strategy.events.dto.EventOneDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SpringBootTest
@Slf4j
class StrategyApplicationTests {

    @Configuration
    @ComponentScan("cz.kb.oleg.strategy")
    static class EventConfig {

        @Bean
        public StrategyHandler<EventDto> eventDtoStrategyHandler(List<EventStrategy<EventDto>> eventStrategies) {
            return new TestableStrategyHandler<>(eventStrategies);
        }

    }

    @Autowired
    StrategyHandler<EventDto> eventDtoStrategyHandler;

    @Test
    void testStrategy() {
        EventOneDto eventOneDto = new EventOneDto("one");
        eventOneDto.setOne("1");
        eventDtoStrategyHandler.handle(eventOneDto);
    }

}
