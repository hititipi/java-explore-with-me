package ru.practicum.main_service.validation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {

    public static final String RESOURCE_NOT_FOUND = "Ресурс не найден";
    public static final String USER_NAME_ALREADY_EXISTS = "Пользователь с таким именем уже существует";
    public static final String CATEGORY_HAS_EVENTS = "Ошибка при удалении категории. Категория содержит события";
    public static final String CATEGORY_NAME_ALREADY_EXISTS = "Категория с таким именем уже существует";
    public static final String EVENT_IS_NOT_WAITING = "Событие не находится в статусе ожидания";
    public static final String CHANGE_PUBLISHED_EVENT = "Нельзя изменять опубликованные события";
    public static final String INVALID_DATES = "Неверно заданы даты";
    public static final String INVALID_TIME_LIMIT = "Время не может быть раньше, чем через два часа от текущего момента";
    public static final String INVALID_LIMIT = "Лимит уже достигнут";
}