package org.sat.common;

import lombok.Data;

@Data
public class ResDto<T> {
    int code=-1;
    String msg="";
    T Data;
    public ResDto() {
        this.code=0;
        this.setData((T) "");
    }

    public ResDto(Object res) {
        this.setData((T) res);
    }
}

