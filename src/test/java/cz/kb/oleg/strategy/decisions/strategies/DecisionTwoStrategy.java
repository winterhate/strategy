package cz.kb.oleg.strategy.decisions.strategies;

import cz.kb.oleg.strategy.api.Result;
import cz.kb.oleg.strategy.decisions.DecisionStrategy;
import cz.kb.oleg.strategy.decisions.dto.DecisionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DecisionTwoStrategy extends DecisionStrategy<DecisionDto> {

    @Override
    public boolean isApplicable(DecisionDto dto) {
        return "two".equals(dto.type());
    }

    @Override
    public Result<Result.Void> apply(DecisionDto dto) {
        log.info("Handled by DecisionTwoStrategy: {}", dto);
        return Result.Ok();
    }

}
