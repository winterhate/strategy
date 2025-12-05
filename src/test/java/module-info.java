module strategyTests {
    requires strategy;
    requires jakarta.validation;
    requires static lombok;
    requires org.junit.jupiter.api;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.boot.test;
    requires spring.context;
    requires org.slf4j;

    opens cz.kb.oleg.strategy;
    opens cz.kb.oleg.strategy.decisions;
    opens cz.kb.oleg.strategy.decisions.strategies;
    opens cz.kb.oleg.strategy.events;
    opens cz.kb.oleg.strategy.events.dto;
}