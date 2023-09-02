package ru.practicum.main_service.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestStats {
    private Long eventId;
    private Long confirmedRequests;
}