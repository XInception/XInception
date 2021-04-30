package org.sat.auth.api.dto;

import lombok.Data;

@Data
public class LoginDto {
    String token;
    UserDto user;
}
