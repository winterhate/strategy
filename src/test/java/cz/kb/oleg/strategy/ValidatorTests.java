package cz.kb.oleg.strategy;

import jakarta.validation.Validator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@Slf4j
class ValidatorTests {

    @Data
    static class Base {
        @NotEmpty
        private String a;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    static class Derived extends Base {
        @NotNull
        private String b;
    }

    @Autowired
    Validator validator;

    @Test
    void test() {
        Base derived = new Derived();
        derived.setA("valueA");
        // derived.setB("valueB"); // Intentionally commented out to trigger validation error

        var violations = validator.validate(derived);
        if (violations.isEmpty()) {
            log.info("Validation passed");
        } else {
            violations.forEach(v -> log.info("Validation error: {} - {}", v.getPropertyPath(), v.getMessage()));
        }
    }

}
