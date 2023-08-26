package ru.practicum.stats.dto.utils;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class Messages {

    public String postHit(String uri) {
        return String.format("Запрос на сохранение информации по эндпойнту: %s", uri);
    }

    public String getStats(Object start, Object end, List<String> uris, boolean unique) {
        return String.format("Запрос на получение статистики: start = %s end = %s uris: %s unique =  %s", start, end, uris, unique);
    }

}
