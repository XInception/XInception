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
@Slf4j
public class AuthServiceImpl implements  AuthService {

    @Autowired
    UserRepository userRepository;

    @Override
    public LoginDto login(LoginForm form) {
        User user = userRepository.findFirstByAccount(form.getAccount()).orElseThrow(() -> {
            throw new BusinessException(BusinessExceptionEnum.ACCOUNT_NOT_FOUND);
        });

        if (ObjectUtils.isEmpty(user.getPassword())) {
            throw new BusinessException("please reset your password");
        }

        if (!user.getPassword().equals(PasswordUtils.Hash(form.getPassword()))) {
            throw new BusinessException(BusinessExceptionEnum.ACCOUNT_NOT_FOUND);
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        LoginDto loginDto = new LoginDto();
        loginDto.setUser(userDto);
        loginDto.setToken(genToken(user));
        return loginDto;
    }

    @Autowired
    RedisCommands<String, String> redisCommands;

    @Override
    public String genToken(User user) {
        var token = UUID.getUUID();
        var key = String.format(RedisKey.TOKEN, token);
        redisCommands.set(key, String.valueOf(user.getId()));
        return token;
    }


    @Override
    public Object signup(SigninForm form) {
        userRepository.findFirstByPhone(form.getPhone()).ifPresent(u -> {
            throw new BusinessException("phone ready exits");
        });
        userRepository.findFirstByEmail(form.getPhone()).ifPresent(u -> {
            throw new BusinessException("email ready exits");
        });
        userRepository.findFirstByAccount(form.getAccount()).ifPresentOrElse(u -> {
            throw new BusinessException("account ready exits");
        }, () -> {
            var newUser = new User();
            newUser.setAccount(form.getAccount());
            newUser.setName(form.getUsername());
            newUser.setPassword(PasswordUtils.Hash(form.getPassword()));
            newUser.setEmail(form.getEmail());
            newUser.setPhone(form.getPhone());
            userRepository.save(newUser);
        });
        return true;
    }


    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public User getUserInfo() {
        String token = "";
        token = httpServletRequest.getHeader("Authorization").split(" ")[1];

        if (ObjectUtils.isEmpty(token)) {
            throw new BusinessException(BusinessExceptionEnum.ACCOUNT_HAS_BEEN_LOGINED_IN_FROM_ELSEWHERE);
        }

        var key = String.format(RedisKey.TOKEN, token);

        var userId = redisCommands.get(key);

        if (ObjectUtils.isEmpty(userId)) {
            throw new BusinessException("token error");
        }

        return userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> {
            throw new BusinessException("user not found");
        });
    }

    @Autowired
    JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    String from;

    @Override
    public Object confirmEmail(ConfirmEmailForm form) {
        log.info("找回邮箱" + form.getEmail());
        userRepository.findFirstByEmail(form.getEmail()).orElseThrow(() -> {
            throw new BusinessException("email not found");
        });
        var code = UUID.getUUID();
        redisCommands.set(String.format(emailCode, form.getEmail()), code, new SetArgs().ex(1000 * 60));
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(from);
            //邮件接收人
            simpleMailMessage.setTo(form.getEmail());
            //邮件主题
            simpleMailMessage.setSubject("找回密码-验证码");
            //邮件内容
            simpleMailMessage.setText("验证码:" + code + "  有效期：60秒");
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.error("邮件发送失败 {}", e.getMessage());
        }
        return true;
    }
    @Override
    public Object resetpassword(ResetPasswordForm form) {
        var user = userRepository.findFirstByEmail(form.getEmail()).orElseThrow(() -> {
            throw new BusinessException("email not found");
        });
        var code = (String) redisCommands.get(String.format(emailCode, form.getEmail()));
        if (!form.getCode().equals(code)) {
            throw new BusinessException("code error");
        } else {
            user.setPassword(PasswordUtils.Hash(form.getPassword()));
            userRepository.save(user);
        }
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(from);
            //邮件接收人
            simpleMailMessage.setTo(form.getEmail());
            //邮件主题
            simpleMailMessage.setSubject("找回密码-确认修改");
            //邮件内容
            simpleMailMessage.setText("您的密码已经修改请重新登录");
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.error("邮件发送失败 {}", e.getMessage());
        }
        return true;
    }
}
