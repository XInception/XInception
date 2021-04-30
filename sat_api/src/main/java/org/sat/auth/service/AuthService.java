package org.sat.auth.service;

import org.sat.auth.api.dto.*;
import org.sat.auth.entity.User;


public interface AuthService {


    LoginDto login(LoginForm form);

    String genToken(User user);


    Object signup(SigninForm form);


    User getUserInfo();


    Object confirmEmail(ConfirmEmailForm form);

    Object resetpassword(ResetPasswordForm form);
}
