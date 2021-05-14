package org.xinc.auth.api.dto;

import lombok.Data;
import org.xinc.common.BaseDto;

@Data
public class UserListDto implements BaseDto {
    Long id;
    String name;
    String email;
    String phone;
    String desc;
    String lastLoginIp;
    String lastLoginTime;
    Boolean isActive;
}