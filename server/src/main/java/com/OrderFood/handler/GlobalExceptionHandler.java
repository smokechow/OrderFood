package com.OrderFood.handler;

import com.OrderFood.constant.MessageConstant;
import com.OrderFood.exception.BaseException;
import com.OrderFood.result.Result;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获SQL异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //  Duplicate entry 'demon91' for key 'employee.idx_username'
        val message = ex.getMessage();
        if(message.contains("Duplicate entry")){
            return Result.error("账号名称"+MessageConstant.ALREADY_EXISTS);//  发送回前端
        }
        return Result.error(ex.getMessage());
    }

}
