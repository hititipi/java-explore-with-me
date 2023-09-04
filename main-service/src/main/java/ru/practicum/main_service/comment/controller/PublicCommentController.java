package ru.practicum.main_service.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.comment.dto.ResponceCommentDto;
import ru.practicum.main_service.comment.service.CommentService;
import ru.practicum.main_service.utils.EwmPageRequest;
import ru.practicum.main_service.utils.Messages;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.main_service.utils.Constants.DEFAULT_PAGE_FROM;
import static ru.practicum.main_service.utils.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Validated
@Slf4j
public class PublicCommentController {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponceCommentDto getComment(@PathVariable Long commentId) {
        log.info(Messages.getComment(commentId));
        return commentService.getComment(commentId);
    }

    @GetMapping("/events/{eventId}")
    public List<ResponceCommentDto> getCommentsByEvent(
            @PathVariable Long eventId,
            @RequestParam(defaultValue = DEFAULT_PAGE_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Positive Integer size) {
        log.info(Messages.getCommentsForEvent(eventId));
        return commentService.getAllCommentByEvent(eventId, EwmPageRequest.of(from, size));
    }

}
