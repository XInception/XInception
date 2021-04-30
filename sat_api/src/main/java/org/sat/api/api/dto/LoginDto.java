package org.sat.api.api.dto;

import lombok.Data;

@Data
public class LoginDto {
    String token;
    UserDto user;
}
