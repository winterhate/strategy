module strategy {

    requires jakarta.validation;
    requires static lombok;
    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires org.slf4j;

    exports cz.kb.oleg.strategy.api;
    exports cz.kb.oleg.strategy.service.dispatcher;
    exports cz.kb.oleg.strategy.service.executors;
    exports cz.kb.oleg.strategy.service.selectors;
    exports cz.kb.oleg.strategy.service.strategies;
    exports cz.kb.oleg.strategy.service.result;

}