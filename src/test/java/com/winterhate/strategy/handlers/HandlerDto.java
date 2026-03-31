package com.winterhate.strategy.handlers;

import com.winterhate.strategy.service.selectors.EnumStrategyData;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HandlerDto extends EnumStrategyData<Handlers> {
    public HandlerDto(Handlers selector, String value) {
        super(selector);
        this.value = value;
    }

    private final String value;

}
