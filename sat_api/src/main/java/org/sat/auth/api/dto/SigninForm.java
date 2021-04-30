package org.sat.auth.api.dto;

import lombok.Data;

@Data
public class SigninForm {
    String account;
    String username;
    String password;
    String email;
    String phone;
}
