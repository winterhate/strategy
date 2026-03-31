package com.winterhate.strategy.handlers;

import com.winterhate.strategy.api.StrategyDispatcher;
import com.winterhate.strategy.api.StrategyExecutor;
import com.winterhate.strategy.api.StrategySelector;
import com.winterhate.strategy.service.dispatcher.DefaultStrategyDispatcher;
import com.winterhate.strategy.service.executors.AsyncStrategyExecutor;
import com.winterhate.strategy.service.executors.DefaultStrategyExecutor;
import com.winterhate.strategy.service.executors.LoggingStrategyExecutor;
import com.winterhate.strategy.service.selectors.EnumStrategySelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class HandlersConfiguration {

    @Bean
    public StrategySelector<HandlerDto, HandlerStrategy> handlerStrategySelector(List<HandlerStrategy> strategies) {
        return new EnumStrategySelector<>(strategies);
    }

    @Bean
    public StrategyExecutor<HandlerDto> handlerStrategyExecutor() {
        return new AsyncStrategyExecutor<>(new LoggingStrategyExecutor<>(new DefaultStrategyExecutor<>()));
    }

    @Bean
    public StrategyDispatcher<HandlerDto> handlerStrategyDispatcher(
            StrategySelector<HandlerDto, HandlerStrategy> handlerStrategySelector,
            StrategyExecutor<HandlerDto> handlerStrategyExecutor
    ) {
        return new DefaultStrategyDispatcher<>(handlerStrategySelector, handlerStrategyExecutor);
    }

}
