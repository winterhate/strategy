package cz.kb.oleg.strategy.events.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class EventOneDto extends EventDto {
    private String one;
    public EventOneDto(String name) {
        super(name);
    }
}
