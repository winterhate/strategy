package com.winterhate.strategy.service.selectors;

import lombok.Getter;

@Getter
public class EnumStrategyData<E extends Enum<E>> {
    private final E selector;

    public EnumStrategyData(E selector) {
        this.selector = selector;
    }

}
