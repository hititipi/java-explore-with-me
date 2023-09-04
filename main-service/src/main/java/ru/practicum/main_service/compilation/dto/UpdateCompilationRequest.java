package ru.practicum.main_service.compilation.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UpdateCompilationRequest {
    private List<Long> events;
    @Size(min = 1, max = 50)
    private String title;
    private Boolean pinned;
}