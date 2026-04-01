package com.winterhate.strategy

import com.winterhate.strategy.api.StrategyDispatcher
import com.winterhate.strategy.api.StrategyExecutor
import com.winterhate.strategy.api.StrategySelector
import com.winterhate.strategy.events.EventStrategy
import com.winterhate.strategy.events.dto.EventDto
import com.winterhate.strategy.events.dto.EventOneDto
import com.winterhate.strategy.events.dto.EventTwoDto
import com.winterhate.strategy.handlers.HandlerDto
import com.winterhate.strategy.handlers.Handlers
import com.winterhate.strategy.service.dispatcher.DefaultStrategyDispatcher
import com.winterhate.strategy.service.dispatcher.ValidatingStrategyDispatcher
import com.winterhate.strategy.service.executors.AsyncStrategyExecutor
import com.winterhate.strategy.service.executors.DefaultStrategyExecutor
import com.winterhate.strategy.service.selectors.CachedStrategySelector
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation.buildDefaultValidatorFactory
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.util.*

@SpringBootTest
class StrategyApplicationTests {

    companion object {
        init {
            Locale.setDefault(Locale.US)
        }
    }

    @Autowired
    lateinit var eventDtoStrategyDispatcher: StrategyDispatcher<EventDto>

    @Autowired
    lateinit var handlerStrategyDispatcher: StrategyDispatcher<HandlerDto>

    @Autowired
    lateinit var log: Logger

    @Test
    fun testStrategy() {
        log.info("Test strategy")
        eventDtoStrategyDispatcher.dispatch(EventOneDto())

        val data = EventTwoDto(detailTwo = "")
        val exception = assertThrows(ConstraintViolationException::class.java) {
            eventDtoStrategyDispatcher.dispatch(data)
        }
        assertEquals("detailTwo: must not be blank", exception.message)

        handlerStrategyDispatcher.dispatch(HandlerDto(Handlers.FIRST, "First handler"))
        handlerStrategyDispatcher.dispatch(HandlerDto(Handlers.SECOND, "Second handler"))
    }


    @Configuration
    @ComponentScan("com.winterhate")
    class EventConfig {

        @Bean
        @Scope(BeanDefinition.SCOPE_PROTOTYPE)
        fun logger(injectionPoint: InjectionPoint): Logger =
            LoggerFactory.getLogger(injectionPoint.methodParameter?.containingClass ?: this::class.java)

        @Bean
        fun eventDtoStrategySelector(eventStrategies: List<EventStrategy<EventDto>>): StrategySelector<EventDto, EventStrategy<EventDto>> =
            CachedStrategySelector(eventStrategies)

        @Bean
        fun eventDtoStrategyExecutor(): StrategyExecutor<EventDto> =
            AsyncStrategyExecutor(DefaultStrategyExecutor())

        @Bean
        fun eventDtoStrategyHandler(
            eventDtoStrategySelector: StrategySelector<EventDto, EventStrategy<EventDto>>,
            eventDtoStrategyExecutor: StrategyExecutor<EventDto>,
            validator: Validator
        ): StrategyDispatcher<EventDto> =
            ValidatingStrategyDispatcher(
                DefaultStrategyDispatcher(eventDtoStrategySelector, eventDtoStrategyExecutor),
                validator
            )

        @Bean
        @ConditionalOnMissingBean
        fun validator(): Validator = buildDefaultValidatorFactory().use { it.validator }

    }

}

