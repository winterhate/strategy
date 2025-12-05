package cz.kb.oleg.strategy.events;

import cz.kb.oleg.strategy.api.Result;
import cz.kb.oleg.strategy.api.StrategyDispatcher;
import cz.kb.oleg.strategy.decisions.dto.DecisionDto;
import cz.kb.oleg.strategy.events.dto.EventDto;
import cz.kb.oleg.strategy.events.dto.EventOneDto;
import cz.kb.oleg.strategy.service.result.NoValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventOneStrategy extends EventStrategy<EventDto> {

    private final StrategyDispatcher<DecisionDto, NoValue> decisionStrategyDispatcher;

    @Override
    public Class<EventOneDto> getApplicableClass() {
        return EventOneDto.class;
    }

    @Override
    public Result<NoValue> apply(EventDto dto) {
        if (!(dto instanceof EventOneDto eventOneDto)) {
            throw new IllegalArgumentException("Invalid dto type: " + dto.getClass());
        }
        log.info("Handled by EventOneStrategy: {}", eventOneDto);
        decisionStrategyDispatcher.dispatch(new DecisionDto("one")).forEach(result -> {
            log.info("Decision one result: {}", result);
            if (result.isFuture()) {
                log.info("1/ Resolved as {}", result.resolve());
            }
        });

        decisionStrategyDispatcher.dispatch(new DecisionDto("two")).forEach(result -> {
            log.info("Decision two result: {}", result);
            if (result.isFuture()) {
                log.info("2/ Resolved as {}", result.resolve());
            }
        });
        return Result.Ok();
    }

}
