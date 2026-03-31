package com.winterhate.strategy.decisions.strategies;

import com.winterhate.strategy.decisions.DecisionStrategy;
import com.winterhate.strategy.decisions.dto.DecisionDto;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DecisionTwoStrategy extends DecisionStrategy<DecisionDto> {

    @Override
    public boolean isApplicable(@NonNull DecisionDto dto) {
        return "two".equals(dto.type());
    }

    @Override
    public void apply(DecisionDto dto) {
        log.info("Handled by DecisionTwoStrategy: {}", dto);
    }

}
