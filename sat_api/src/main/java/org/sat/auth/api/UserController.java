package org.sat.auth.api;


import org.sat.auth.api.dto.UserDto;
import org.sat.auth.api.dto.UserForm;
import org.sat.auth.api.dto.UserListDto;
import org.sat.auth.api.dto.UserQueryForm;
import org.sat.auth.entity.User;
import org.sat.auth.service.UserService;
import org.sat.common.BaseController;
import org.sat.common.Page;
import org.sat.common.ResDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;


    @GetMapping("/list")
    public ResDto<Page<UserListDto>> list(UserQueryForm query) {
        Page<UserListDto> ret = new Page<>();
        org.springframework.data.domain.Page<User> res = userService.getUserList(query);
        res.forEach(s -> {
            UserListDto t = new UserListDto();
            BeanUtils.copyProperties(s, t);
            ret.getList().add(t);
        });
        return success(ret);
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestParam("id") long id) {
        return success(userService.removeUser(id));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save(@RequestBody UserForm userForm) {
        return success(userService.saveUser(userForm));
    }

    @GetMapping("/query")
    public ResDto<UserDto> query(@RequestParam("uuid") String uuid) {
        return success(userService.queryUser(uuid));
    }

}
