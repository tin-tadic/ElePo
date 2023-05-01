package com.faks.elepo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetUserRoleDTO {
    @NotBlank(message = "Role is mandatory!")
    private String role;
}
