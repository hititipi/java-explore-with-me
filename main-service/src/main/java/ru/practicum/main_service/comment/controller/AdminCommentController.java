package ru.practicum.main_service.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/admin/comments")
@Validated
@Slf4j
public class AdminCommentController {

    private final CommentService commentService;

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long commentId) {
        log.info(Messages.deleteComment(commentId));
        commentService.deleteCommentByAdmin(commentId);
    }

    @PatchMapping("/{commentId}")
    public ResponceCommentDto approveComment(@PathVariable Long commentId) {
        log.info(Messages.approveComment(commentId));
        return commentService.approveComment(commentId);
    }

    @GetMapping
    public List<ResponceCommentDto> getAll(
            @RequestParam(defaultValue = "false") Boolean pending,
            @RequestParam(defaultValue = DEFAULT_PAGE_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Positive Integer size) {
        log.info(Messages.getAllComments());
        return commentService.getAllComments(pending, EwmPageRequest.of(from, size));
    }

    @GetMapping("events/{eventId}")
    public List<ResponceCommentDto> getAllByEvent(
            @PathVariable Long eventId,
            @RequestParam(defaultValue = DEFAULT_PAGE_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Positive Integer size) {
        log.info(Messages.getCommentsForEvent(eventId));
        return commentService.getAllEventCommentsByAdmin(eventId, EwmPageRequest.of(from, size));
    }

}
