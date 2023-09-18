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

    public String addCategory() {
        return "Запрос на создание категории";
    }

    public String patchCategory(Long id) {
        return String.format("Запрос на изменение пользователя: id = %d", id);
    }

    public String deleteCategory(Long id) {
        return String.format("Запрос на удаление категории: id = %d", id);
    }

    public String getCategory(Long id) {
        return String.format("Запрос на получение категории: id = %d", id);
    }

    public String getAllCategories() {
        return "Запрос на получение всех категорий";
    }

    public String getPublicEvent(Long id) {
        return String.format("Запрос на получение события: id = %d", id);
    }

    public String getPublicEvents() {
        return "Запрос на получение событий";
    }

    public String getAdminEvents() {
        return "Запрос на поиск событий";
    }

    public String patchEvent(Long eventId) {
        return String.format("Запрос на изменение события: event_id = %d", eventId);
    }

    public String getPrivateEvents(Long userId) {
        return String.format("Запрос на получение событий добавленных пользователем: user_id = %d", userId);
    }

    public String addEvent(Long userId) {
        return String.format("Запрос на добавление события: user_id = %d", userId);
    }

    public String getPrivateEvent(Long userId, Long eventId) {
        return String.format("Запрос на получение события: user_id = %d event_id = %d", userId, eventId);
    }

    public String pathEvent(Long userId, Long eventId) {
        return String.format("Запрос на изменение события: user_id = %d event_id = %d", userId, eventId);
    }

    public String getEventRequests(Long userId, Long eventId) {
        return String.format("Запрос на получение информации о запросах: user_id = %d event_id = %d", userId, eventId);
    }

    public String pathEventRequests(Long userId, Long eventId) {
        return String.format("Запрос на изменение статуса запросов: user_id = %d event_id = %d", userId, eventId);
    }

    public String getRequests(Long userId) {
        return String.format("Запрос на получение зявок: user_id = %d", userId);
    }

    public String addRequest(Long userId, Long requestId) {
        return String.format("Запрос на создание зявки: user_id = %d request_id = %d", userId, requestId);
    }

    public String cancelRequest(Long userId, Long requestId) {
        return String.format("Запрос на отмену зявки: user_id = %d request_id = %d", userId, requestId);
    }

    public String postHit(String uri) {
        return String.format("Запрос на сохранение информации по эндпойнту: %s", uri);
    }

    public String getStats(Object start, Object end, List<String> uris, boolean unique) {
        return String.format("Запрос на получение статистики: start = %s end = %s uris: %s unique =  %s", start, end, uris, unique);
    }

    public String addCompilation() {
        return "Запрос на создание подборки";
    }

    public String deleteCompilation(Long id) {
        return String.format("Запрос на удаление подборки: id = %d", id);
    }

    public String patchCompilation(Long id) {
        return String.format("Запрос на изменение подборки: id = %d", id);
    }

    public String getCompilation(Long id) {
        return String.format("Запрос на получение подборки: id = %d", id);
    }

    public String getAllCompilations() {
        return "Запрос на получение всех подборок";
    }

    public static String addComment(Long userId, Long eventId) {
        return String.format("Запрос на создание комментария: user_id = %d event_id = %d", userId, eventId);
    }

    public static String patchComment(Long userId, Long commentId) {
        return String.format("Запрос на изменения комментария: user_id = %d comment_id = %d", userId, commentId);
    }

    public static String getAllComments(Long userId) {
        return String.format("Запрос на получение комментариев: user_id = %d", userId);
    }

    public static String getAllComments() {
        return "Запрос на получение комментариев";
    }

    public static String deleteComment(Long userId, Long commentId) {
        return String.format("Запрос на удаление комментария: user_id = %d comment_id = %d", userId, commentId);
    }

    public static String deleteComment(Long commentId) {
        return String.format("Запрос на удаление комментария: comment_id = %d", commentId);
    }

    public static String getComment(Long commentId) {
        return String.format("Запрос на получение комментария: comment_id = %d", commentId);
    }

    public static String getCommentsForEvent(Long eventId) {
        return String.format("Запрос на получение комментариев для события: event_id = %d", eventId);
    }

    public static String approveComment(Long commentId) {
        return String.format("Запрос на одобрение комментармя: comment_id = %d", commentId);
    }

}
