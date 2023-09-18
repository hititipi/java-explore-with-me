package ru.practicum.main_service.comment.dto;

import lombok.*;
import ru.practicum.main_service.comment.model.CommentStatus;
import ru.practicum.main_service.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponceCommentDto {

    private Long id;
    private String text;
    private LocalDateTime created;
    private UserShortDto author;
    private Long eventId;
    private CommentStatus commentStatus;

}
