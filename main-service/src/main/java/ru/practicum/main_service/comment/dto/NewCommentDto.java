package ru.practicum.main_service.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCommentDto {

    @NotBlank
    private String text;

}
