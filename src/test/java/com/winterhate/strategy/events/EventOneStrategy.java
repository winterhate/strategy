package com.winterhate.strategy.events;

import com.winterhate.strategy.api.StrategyDispatcher;
import com.winterhate.strategy.decisions.dto.DecisionDto;
import com.winterhate.strategy.events.dto.EventDto;
import com.winterhate.strategy.events.dto.EventOneDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventOneStrategy extends EventStrategy<EventDto> {

    private final StrategyDispatcher<DecisionDto> decisionStrategyDispatcher;

    @Override
    @NonNull
    public Class<EventOneDto> getApplicableClass() {
        return EventOneDto.class;
    }

    @Override
    public void apply(EventDto dto) {
        if (!(dto instanceof EventOneDto eventOneDto)) {
            throw new IllegalArgumentException("Invalid dto type: " + dto.getClass());
        }
        log.info("Handled by EventOneStrategy: {}", eventOneDto);
        decisionStrategyDispatcher.dispatch(new DecisionDto("one"));
        decisionStrategyDispatcher.dispatch(new DecisionDto("two"));
    }

}
