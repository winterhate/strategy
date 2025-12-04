package cz.kb.oleg.strategy.events.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EventTwoDto extends EventDto {

    @NotBlank
    private String detailTwo = "EventTwoDetail";

}
