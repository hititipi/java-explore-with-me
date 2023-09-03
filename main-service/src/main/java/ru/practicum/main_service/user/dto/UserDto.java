package ru.practicum.main_service.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private long id;
    @Size(min = 2, max = 250)
    @NotBlank
    private String name;
    @Size(min = 6, max = 254)
    private String email;
}