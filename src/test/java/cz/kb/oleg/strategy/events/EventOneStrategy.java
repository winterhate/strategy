package cz.kb.oleg.strategy.events;

import cz.kb.oleg.strategy.api.StrategyDispatcher;
import cz.kb.oleg.strategy.decisions.dto.DecisionDto;
import cz.kb.oleg.strategy.events.dto.EventDto;
import cz.kb.oleg.strategy.events.dto.EventOneDto;
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
