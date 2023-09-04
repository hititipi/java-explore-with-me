package ru.practicum.main_service.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.main_service.event.model.EventStateAction;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.main_service.utils.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UpdateEventUserRequest {

    @Size(min = MIN_LENGTH_EVENT_ANNOTATION, max = MAX_LENGTH_EVENT_ANNOTATION)
    private String annotation;
    private Long category;
    @Size(min = MIN_LENGTH_EVENT_DESCRIPTION, max = MAX_LENGTH_EVENT_DESCRIPTION)
    private String description;
    @JsonFormat(pattern = DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;
    @Valid
    private LocationDto location;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration;
    private EventStateAction stateAction;
    @Size(min = MIN_LENGTH_EVENT_TITLE, max = MAX_LENGTH_EVENT_TITLE)
    private String title;
}
