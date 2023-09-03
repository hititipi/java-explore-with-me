package ru.practicum.main_service.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import ru.practicum.main_service.event.model.EventState;

import java.time.LocalDateTime;

import static ru.practicum.main_service.utils.Constants.DATE_FORMAT;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventFullDto {

    @JsonUnwrapped
    private EventShortDto eventShortDto;

    private String description;
    private EventState state;
    private LocationDto location;
    private Integer participantLimit;
    @JsonFormat(pattern = DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdOn;
    @JsonFormat(pattern = DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime publishedOn;
    private Boolean requestModeration;

}
