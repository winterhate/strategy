package com.winterhate.strategy.decisions

import com.winterhate.strategy.api.*
import com.winterhate.strategy.decisions.dto.DecisionDto
import com.winterhate.strategy.service.dispatcher.DefaultStrategyDispatcher
import com.winterhate.strategy.service.dispatcher.ValidatingStrategyDispatcher
import com.winterhate.strategy.service.executors.AsyncStrategyExecutor
import com.winterhate.strategy.service.executors.DefaultStrategyExecutor
import com.winterhate.strategy.service.executors.DefaultStrategyResultHandler
import com.winterhate.strategy.service.executors.LoggingStrategyExecutor
import com.winterhate.strategy.service.selectors.TestableStrategySelector
import jakarta.validation.Validator
import org.slf4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DecisionConfiguration {

    @Bean
    fun decisionStrategySelector(strategies: List<DecisionStrategy<DecisionDto>>): StrategySelector<DecisionDto, DecisionStrategy<DecisionDto>> =
        TestableStrategySelector(strategies)

    @Bean
    fun decisionStrategyExecutorExceptionHandler(log: Logger): StrategyResultHandler<DecisionDto> =
        object : DefaultStrategyResultHandler<DecisionDto>() {
            override fun onException(strategy: Strategy<DecisionDto>, data: DecisionDto, exception: Exception) {
                val strategyName = getStrategyName(strategy)
                log.warn("Strategy {} execution failed with error: {}", strategyName, exception.message)
            }
        }

    @Bean
    fun decisionStrategyExecutor(
        decisionStrategyResultHandler: StrategyResultHandler<DecisionDto>,
        log: Logger
    ): StrategyExecutor<DecisionDto> =
        AsyncStrategyExecutor(LoggingStrategyExecutor(DefaultStrategyExecutor(decisionStrategyResultHandler), log))

    @Bean
    fun decisionStrategyHandler(
        decisionStrategySelector: StrategySelector<DecisionDto, DecisionStrategy<DecisionDto>>,
        decisionStrategyExecutor: StrategyExecutor<DecisionDto>,
        validator: Validator
    ): StrategyDispatcher<DecisionDto> =
        ValidatingStrategyDispatcher(DefaultStrategyDispatcher(decisionStrategySelector, decisionStrategyExecutor), validator)

}

