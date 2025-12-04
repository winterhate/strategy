package cz.kb.oleg.strategy.events.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class EventOneDto extends EventDto {
    private String detailOne = "EventOneDetail";
}
