package cz.kb.oleg.strategy.decisions.strategies;

import cz.kb.oleg.strategy.decisions.DecisionStrategy;
import cz.kb.oleg.strategy.decisions.dto.DecisionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DecisionOneStrategy extends DecisionStrategy<DecisionDto> {

    @Override
    public boolean canHandle(DecisionDto dto) {
        return "one".equals(dto.type());
    }

    @Override
    public void handle(DecisionDto dto) {
        log.info("Handled by DecisionOneStrategy: {}", dto);
    }

}
