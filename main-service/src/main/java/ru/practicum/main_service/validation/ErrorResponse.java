package ru.practicum.main_service.validation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.main_service.utils.Constants;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private String message;
    private String reason;
    private String status;
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private LocalDateTime timestamp;

}