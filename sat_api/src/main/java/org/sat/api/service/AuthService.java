package org.sat.api.service;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.sat.api.RedisKey;
import org.sat.api.api.dto.*;
import org.sat.api.entity.repo.UserRepository;
import org.sat.api.entity.user.User;
import org.sat.api.exception.BusinessException;
import org.sat.api.exception.BusinessExceptionEnum;
import org.sat.api.utils.PasswordUtils;
import org.sat.api.utils.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

import static org.sat.api.RedisKey.emailCode;


@Service

public interface AuthService {


    public LoginDto login(LoginForm form);

    public String genToken(User user);


    public Object signup(SigninForm form);


    public User getUserInfo();


    public Object confirmEmail(ConfirmEmailForm form);

    public Object resetpassword(ResetPasswordForm form);
}
