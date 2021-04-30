package org.sat.api.handler;



import lombok.extern.slf4j.Slf4j;
import org.sat.api.api.dto.ResDto;
import org.sat.api.exception.BusinessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResDto handlerBusinessException(BusinessException ex) {
        return ResUtils.fail(ex.getCode(), ex.getMessage(), ex.getData());
    }

    /**
     * TODO 写入日志
     *
     * @param
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResDto exceptionGet(Exception e) {
        e.printStackTrace();
        return ResUtils.fail(-1, e.getMessage(),"");
    }


}
