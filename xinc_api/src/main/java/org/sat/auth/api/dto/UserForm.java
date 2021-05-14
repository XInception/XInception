package org.xinc.auth.api.dto;


import lombok.Data;
import org.xinc.common.utils.PasswordUtils;
import org.springframework.util.ObjectUtils;

@Data
public class UserForm {
    String name;
    String account;
    String password;
    String email;
    String phone;
    String description;

    String getPassword(){
        if(ObjectUtils.isEmpty(password)){
            return PasswordUtils.Hash("123456");
        }else {
            return  PasswordUtils.Hash(this.password);
        }
    }
}
