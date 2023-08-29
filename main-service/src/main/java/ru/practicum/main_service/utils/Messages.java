package ru.practicum.main_service.utils;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class Messages {

    public static String addUser() {
        return "Запрос на создание пользователя";
    }

    public static String getUsers(List<Long> ids) {
        return String.format("Запрос на получение пользователей: %s", ids);
    }

    public static String deleteUser(long id) {
        return String.format("Запрос на удаление пользователя: id = %d", id);
    }

}
