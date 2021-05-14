package org.xinc.common.handler;


import org.xinc.common.ResDto;

public class ResUtils {
    public static ResDto fail(Integer code, String message, Object data) {
        ResDto res = new ResDto();
        res.setCode(code);
        res.setMsg(message);
        res.setData(data);
        return res;
    }
}
