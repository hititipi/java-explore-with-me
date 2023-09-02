package ru.practicum.main_service.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.user.dto.NewUserRequest;
import ru.practicum.main_service.user.dto.UserDto;
import ru.practicum.main_service.user.service.UserService;
import ru.practicum.main_service.utils.EwmPageRequest;
import ru.practicum.main_service.utils.Messages;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.main_service.utils.Constants.DEFAULT_PAGE_FROM;
import static ru.practicum.main_service.utils.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Validated
@Slf4j
public class UserAdminController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody NewUserRequest newUserRequest) {
        log.info(Messages.addUser());
        return userService.createUser(newUserRequest);
    }

    @GetMapping
    public List<UserDto> getUsers(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(defaultValue = DEFAULT_PAGE_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Positive Integer size) {
        log.info(Messages.getUsers(ids));
        return userService.getUsers(ids, EwmPageRequest.of(from, size));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long userId) {
        log.info(Messages.deleteUser(userId));
        userService.deleteUser(userId);
    }

}
