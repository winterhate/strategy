package cz.kb.oleg.strategy.events;

import cz.kb.oleg.strategy.api.StrategyHandler;
import cz.kb.oleg.strategy.decisions.dto.DecisionDto;
import cz.kb.oleg.strategy.events.dto.EventDto;
import cz.kb.oleg.strategy.events.dto.EventOneDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventOneStrategy extends EventStrategy<EventDto> {

    private final StrategyHandler<DecisionDto> decisionStrategyHandler;

    @Override
    public boolean canHandle(EventDto dto) {
        if (dto instanceof EventOneDto eventOneDto) {
            return "one".equals(dto.getName());
        }
        return false;
    }

    @Override
    public void handle(EventDto dto) {
        if (!(dto instanceof EventOneDto eventOneDto)) {
            return;
        }
        log.info("Handled by EventOneStrategy: {}", eventOneDto);

        decisionStrategyHandler.handle(new DecisionDto("one"));
        decisionStrategyHandler.handle(new DecisionDto("two"));
    }

}
