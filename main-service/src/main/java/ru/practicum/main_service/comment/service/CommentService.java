package ru.practicum.main_service.comment.service;

import ru.practicum.main_service.comment.dto.NewCommentDto;
import ru.practicum.main_service.comment.dto.ResponceCommentDto;
import ru.practicum.main_service.utils.EwmPageRequest;

import java.util.List;

public interface CommentService {

    ResponceCommentDto createComment(Long userId, Long eventId, NewCommentDto newCommentDto);

    ResponceCommentDto patchComment(Long userId, Long commentId, NewCommentDto newCommentDto);

    List<ResponceCommentDto> getAllCommentsByUser(Long userId, EwmPageRequest of);

    List<ResponceCommentDto> getAllComments(Boolean pending, EwmPageRequest of);

    void deleteComment(Long userId, Long commentId);

    ResponceCommentDto getComment(Long commentId);

    List<ResponceCommentDto> getAllCommentByEvent(Long eventId, EwmPageRequest of);

    void deleteCommentByAdmin(Long commentId);

    ResponceCommentDto approveComment(Long commentId);

    List<ResponceCommentDto> getAllEventCommentsByAdmin(Long eventId, EwmPageRequest of);
}
