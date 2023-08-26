package ru.practicum.stats.server.model;

import lombok.experimental.UtilityClass;
import ru.practicum.stats.dto.model.EndpointHitDto;

@UtilityClass
public class StatsMapper {

       public Stats toViewStats(EndpointHitDto endpoint){
              return Stats.builder()
                      .uri(endpoint.getUri())
                      .app(endpoint.getApp())
                      .ip(endpoint.getIp())
                      .timestamp(endpoint.getTimestamp())
                      .build();
       }

}
