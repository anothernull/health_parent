package com.itheima.health.controller;


import com.itheima.health.entity.Result;
import com.itheima.health.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(MyExceptionAdvice.class);

    /**
     * 业务异常的处理
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    public Result handlerMyExceptionAdvice(MyException e){
        return new Result(true, e.getMessage());
    }

    /**
     * 未知异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handlerExceptionAdvice(MyException e){
        //记录异常信息
        log.error("发生未知异常", e);
        return new Result(true, "发生未知异常");
    }
}
