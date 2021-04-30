package org.sat.api.handler;


import org.sat.api.api.dto.ResDto;

public class ResUtils {
    public static ResDto fail(Integer code, String message, Object data) {
        ResDto res = new ResDto();
        res.setCode(code);
        res.setMsg(message);
        res.setData(data);
        return res;
    }
}
