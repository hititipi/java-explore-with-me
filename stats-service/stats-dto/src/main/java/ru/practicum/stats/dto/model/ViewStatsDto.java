package ru.practicum.stats.dto.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ViewStatsDto {

    private String app;
    private String uri;
    private long hits;

}