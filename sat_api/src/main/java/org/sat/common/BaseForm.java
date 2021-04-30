package org.sat.common;

import lombok.Data;

@Data
public class BaseForm {
    /**
     * 访问者的用户id
     */
    public Long getUserId(){
        return 0L;
    }
}
