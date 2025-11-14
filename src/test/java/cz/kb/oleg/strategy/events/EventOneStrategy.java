package cz.kb.oleg.strategy.events;

import cz.kb.oleg.strategy.api.StrategyHandler;
import cz.kb.oleg.strategy.decisions.dto.DecisionDto;
import cz.kb.oleg.strategy.events.dto.EventDto;
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
        return "one".equals(dto.name());
    }

    @Override
    public void handle(EventDto dto) {
        log.info("Handled by EventOneStrategy: {}", dto);

        decisionStrategyHandler.handle(new DecisionDto("one"));
        decisionStrategyHandler.handle(new DecisionDto("two"));
    }

}
