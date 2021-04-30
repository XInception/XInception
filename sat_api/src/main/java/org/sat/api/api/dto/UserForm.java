package org.sat.api.api.dto;

import com.dj.iotlite.utils.PasswordUtils;
import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class UserForm {
    String name;
    String account;
    String password;
    String email;
    String phone;
    String description;
    Long organizationId;

    String getPassword(){
        if(ObjectUtils.isEmpty(password)){
            return PasswordUtils.Hash("123456");
        }else {
            return  PasswordUtils.Hash(this.password);
        }
    }
}
