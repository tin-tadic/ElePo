package com.faks.elepo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggedInUserInfoDTO {
    private Long id;
    private String email;
    private String username;
    private String role;
}
