package com.cwca.common.Exception;

import com.cwca.common.result.Message;
import com.cwca.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/31 - 13:38
 * @description ：全局异常处理
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(LotException.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        Result result = new Result(Message.FAILURE);
        result.addMessage(e.getMessage());
        return result;
    }

}
