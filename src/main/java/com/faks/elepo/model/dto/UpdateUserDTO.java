package com.faks.elepo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    @NotBlank(message = "Name must not be empty!")
    private String username;
    @Email(message = "This is not a valid email!")
    private String email;
    @NotBlank(message = "Password must not be empty!")
    private String password;
}
