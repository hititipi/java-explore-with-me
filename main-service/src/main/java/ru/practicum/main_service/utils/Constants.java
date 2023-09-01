package ru.practicum.main_service.utils;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class Constants {

    public static final String DEFAULT_PAGE_FROM = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final int MIN_LENGTH_EVENT_ANNOTATION = 20;
    public static final int MAX_LENGTH_EVENT_ANNOTATION = 2000;
    public static final int MIN_LENGTH_EVENT_DESCRIPTION = 20;
    public static final int MAX_LENGTH_EVENT_DESCRIPTION = 7000;
    public static final int MIN_LENGTH_EVENT_TITLE = 3;
    public static final int MAX_LENGTH_EVENT_TITLE = 120;
    public static final int EVENT_TIME_LIMIT = 2;
    public static final int EVENT_PATH_TIME_LIMIT = 1;
}

