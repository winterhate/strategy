package cz.kb.oleg.strategy.events.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EventDto {
    @NotBlank
    private String name = "Event Name";
}
