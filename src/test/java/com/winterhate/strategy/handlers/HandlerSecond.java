package com.winterhate.strategy.handlers;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandlerSecond extends HandlerStrategy {
    @Override
    public @NonNull Handlers handles() {
        return Handlers.SECOND;
    }

    @Override
    public void apply(HandlerDto handlerDto) {
        log.info("Handled by HandlerSecond: {}", handlerDto);
    }
}
