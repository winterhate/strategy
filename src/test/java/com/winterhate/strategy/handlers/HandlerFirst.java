package com.winterhate.strategy.handlers;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandlerFirst extends HandlerStrategy {
    @Override
    public @NonNull Handlers handles() {
        return Handlers.FIRST;
    }

    @Override
    public void apply(HandlerDto handlerDto) {
        log.info("Handled by HandlerFirst: {}", handlerDto);
    }
}
