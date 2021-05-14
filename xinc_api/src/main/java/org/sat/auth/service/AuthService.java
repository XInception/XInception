package org.xinc.auth.service;

import org.xinc.auth.api.dto.*;
import org.xinc.auth.entity.User;


public interface AuthService {


    LoginDto login(LoginForm form);

    String genToken(User user);


    Object signup(SigninForm form);


    User getUserInfo();


    Object confirmEmail(ConfirmEmailForm form);

    Object resetpassword(ResetPasswordForm form);
}
