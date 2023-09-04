package ru.practicum.main_service.comment.model;

import lombok.experimental.UtilityClass;
import ru.practicum.main_service.comment.dto.NewCommentDto;
import ru.practicum.main_service.comment.dto.ResponceCommentDto;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.user.model.User;
import ru.practicum.main_service.user.model.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CommentMapper {

    public Comment toComment(User user, Event event, NewCommentDto newCommentDto) {
        return Comment.builder()
                .author(user)
                .event(event)
                .text(newCommentDto.getText())
                .created(LocalDateTime.now())
                .status(CommentStatus.ON_MODERATION)
                .build();
    }

    public ResponceCommentDto toResponceCommentDto(Comment comment) {
        return ResponceCommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .author(UserMapper.toUserShortDto(comment.getAuthor()))
                .commentStatus(comment.getStatus())
                .created(comment.getCreated())
                .eventId(comment.getEvent().getId())
                .build();
    }

    public List<ResponceCommentDto> toResponceCommentsDto(List<Comment> comments) {
        return comments.stream().map(CommentMapper::toResponceCommentDto).collect(Collectors.toList());
    }
}
