package ru.practicum.main_service.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.comment.CommentRepository;
import ru.practicum.main_service.comment.dto.NewCommentDto;
import ru.practicum.main_service.comment.dto.ResponceCommentDto;
import ru.practicum.main_service.comment.model.Comment;
import ru.practicum.main_service.comment.model.CommentMapper;
import ru.practicum.main_service.comment.model.CommentStatus;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.model.EventState;
import ru.practicum.main_service.event.service.EventService;
import ru.practicum.main_service.user.model.User;
import ru.practicum.main_service.user.service.UserService;
import ru.practicum.main_service.utils.EwmPageRequest;
import ru.practicum.main_service.validation.ConflitException;
import ru.practicum.main_service.validation.ValidationException;

import java.util.List;
import java.util.Objects;

import static ru.practicum.main_service.validation.ErrorMessages.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    @Transactional
    public ResponceCommentDto createComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        User user = userService.getUser(userId);
        Event event = eventService.getEventById(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflitException(REQUEST_NOT_PUBLISHED_EVENT);
        }
        Comment newComment = CommentMapper.toComment(user, event, newCommentDto);
        return CommentMapper.toResponceCommentDto(commentRepository.save(newComment));
    }

    @Override
    @Transactional
    public ResponceCommentDto patchComment(Long userId, Long commentId, NewCommentDto newCommentDto) {
        userService.getUser(userId);
        Comment comment = findComment(commentId);
        checkOwner(comment.getAuthor().getId(), userId);
        comment.setText(newCommentDto.getText());
        return CommentMapper.toResponceCommentDto(commentRepository.save(comment));
    }

    @Override
    public ResponceCommentDto getComment(Long id) {
        return CommentMapper.toResponceCommentDto(findComment(id));
    }

    @Override
    public List<ResponceCommentDto> getAllCommentsByUser(Long userId, EwmPageRequest page) {
        userService.getUser(userId);
        List<Comment> result = commentRepository.findAllByAuthorId(userId, page);
        return CommentMapper.toResponceCommentsDto(result);
    }

    @Override
    public List<ResponceCommentDto> getAllComments(Boolean pending, EwmPageRequest page) {
        if (pending) {
            return CommentMapper.toResponceCommentsDto(commentRepository.findAllByStatus(CommentStatus.ON_MODERATION));
        } else {
            return CommentMapper.toResponceCommentsDto(commentRepository.findAll());
        }
    }

    @Override
    public List<ResponceCommentDto> getAllCommentByEvent(Long eventId, EwmPageRequest page) {
        Event event = eventService.getEventById(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflitException(REQUEST_NOT_PUBLISHED_EVENT);
        }
        return CommentMapper.toResponceCommentsDto(commentRepository.findAllByEventId(eventId, page));
    }

    @Override
    public List<ResponceCommentDto> getAllEventCommentsByAdmin(Long eventId, EwmPageRequest page) {
        eventService.getEventById(eventId);
        return CommentMapper.toResponceCommentsDto(commentRepository.findAllByEventIdAndStatus(eventId, CommentStatus.ON_MODERATION, page));
    }

    @Override
    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        userService.getUser(userId);
        Comment comment = findComment(commentId);
        checkOwner(comment.getAuthor().getId(), userId);
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void deleteCommentByAdmin(Long commentId) {
        findComment(commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public ResponceCommentDto approveComment(Long commentId) {
        Comment comment = findComment(commentId);
        comment.setStatus(CommentStatus.APPROVED);
        return CommentMapper.toResponceCommentDto(commentRepository.save(comment));

    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND));
    }

    private void checkOwner(Long authorId, Long userId) {
        if (!Objects.equals(authorId, userId)) {
            throw new ConflitException(NOT_OWNER);
        }
    }
}
