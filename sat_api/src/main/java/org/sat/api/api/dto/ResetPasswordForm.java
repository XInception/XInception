package org.sat.api.api.dto;

import lombok.Data;

@Data
public class ResetPasswordForm {
    String email;
    String password;
    String code;
}
