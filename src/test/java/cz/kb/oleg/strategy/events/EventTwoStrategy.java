package cz.kb.oleg.strategy.events;

import cz.kb.oleg.strategy.events.dto.EventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventTwoStrategy extends EventStrategy<EventDto> {

    @Override
    public boolean canHandle(EventDto dto) {
        return "two".equals(dto.name());
    }

    @Override
    public void handle(EventDto dto) {
        log.info("Handled by EventTwoStrategy: " + dto);
    }

}
