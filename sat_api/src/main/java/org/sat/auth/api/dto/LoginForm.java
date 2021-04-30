package org.sat.auth.api.dto;

import lombok.Data;

@Data
public class LoginForm {
    String account;
    String password;
}
