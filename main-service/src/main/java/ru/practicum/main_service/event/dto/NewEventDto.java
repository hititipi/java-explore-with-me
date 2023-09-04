package ru.practicum.main_service.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class NewEventDto {

    @NotBlank
    @Size(min = MIN_LENGTH_EVENT_ANNOTATION, max = MAX_LENGTH_EVENT_ANNOTATION)
    private String annotation;
    @NotNull
    private Long category;
    @NotBlank
    @Size(min = MIN_LENGTH_EVENT_DESCRIPTION, max = MAX_LENGTH_EVENT_DESCRIPTION)
    private String description;
    @NotNull
    @JsonFormat(pattern = DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;
    @NotNull
    private LocationDto location;
    private Boolean paid = false;
    @PositiveOrZero
    private Integer participantLimit = 0;
    private Boolean requestModeration = true;
    @NotBlank
    @Size(min = MIN_LENGTH_EVENT_TITLE, max = MAX_LENGTH_EVENT_TITLE)
    private String title;
}