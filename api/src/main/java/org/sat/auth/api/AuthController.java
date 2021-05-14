package org.xinc.auth.api;



import org.xinc.auth.api.dto.*;
import org.xinc.auth.service.AuthService;
import org.xinc.common.BaseController;
import org.xinc.common.ResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController extends BaseController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResDto<LoginDto> login(@RequestBody LoginForm Loginform) {
        return success(authService.login(Loginform));
    }

    @PostMapping("/signup")
    public ResDto<Boolean> signup(@RequestBody SigninForm form) {
        return success(authService.signup(form));
    }


    @PostMapping("/confirmEmail")
    public ResDto<Boolean> confirmEmail(@RequestBody ConfirmEmailForm form) {
        return success(authService.confirmEmail(form));
    }

    @PostMapping("/resetpassword")
    public ResDto<Boolean> resetpassword(@RequestBody ResetPasswordForm form) {
        return success(authService.resetpassword(form));
    }

}
