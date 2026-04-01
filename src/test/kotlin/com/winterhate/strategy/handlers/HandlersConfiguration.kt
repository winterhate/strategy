package com.winterhate.strategy.handlers

import com.winterhate.strategy.api.StrategyDispatcher
import com.winterhate.strategy.api.StrategyExecutor
import com.winterhate.strategy.api.StrategySelector
import com.winterhate.strategy.service.dispatcher.DefaultStrategyDispatcher
import com.winterhate.strategy.service.executors.AsyncStrategyExecutor
import com.winterhate.strategy.service.executors.DefaultStrategyExecutor
import com.winterhate.strategy.service.executors.LoggingStrategyExecutor
import com.winterhate.strategy.service.selectors.EnumStrategySelector
import org.slf4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HandlersConfiguration {

    @Bean
    fun handlerStrategySelector(strategies: List<HandlerStrategy>): StrategySelector<HandlerDto, HandlerStrategy> =
        EnumStrategySelector(strategies)

    @Bean
    fun handlerStrategyExecutor(log: Logger): StrategyExecutor<HandlerDto> =
        AsyncStrategyExecutor(LoggingStrategyExecutor(DefaultStrategyExecutor(), log))

    @Bean
    fun handlerStrategyDispatcher(
        handlerStrategySelector: StrategySelector<HandlerDto, HandlerStrategy>,
        handlerStrategyExecutor: StrategyExecutor<HandlerDto>
    ): StrategyDispatcher<HandlerDto> =
        DefaultStrategyDispatcher(handlerStrategySelector, handlerStrategyExecutor)
}

