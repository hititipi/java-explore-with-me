package ru.practicum.main_service.utils;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class Messages {

    public String addUser() {
        return "Запрос на создание пользователя";
    }

    public String getUsers(List<Long> ids) {
        return String.format("Запрос на получение пользователей: %s", ids);
    }

    public String deleteUser(long id) {
        return String.format("Запрос на удаление пользователя: id = %d", id);
    }

    public String addCategory(){
        return "Запрос на создание категории";
    }
    public String pathCategory(Long id) {
        return String.format("Запрос на изменение пользователя: id = %d", id);
    }

    public String deleteCategory(Long id) {
        return String.format("Запрос на удаление категории: id = %d", id);
    }

    public String getCategory(Long id) {
        return String.format("Запрос на получение категории: id = %d", id);
    }

    public String getAllCategories(){
        return "Запрос на получение всех категорий";
    }

}
