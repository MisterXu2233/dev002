package com.fc.advice;

import com.fc.vo.ResultVO;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//用于对Controller进行增强
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseBody
    public ResultVO exception(Exception e){
        return new ResultVO(4500,"输入了重复的名字，请换个名字",false,e.getMessage());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResultVO httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        return new ResultVO(5000,"请求方式出错，返回更改请求方式",false,e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResultVO exception(RuntimeException e){
        return new ResultVO(5000,"系统操作异常，请联系管理员",false,e.getMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public ResultVO missingServletRequestParameterException(MissingServletRequestParameterException e){
        return new ResultVO(4600,"缺少了重要的参数，请返回重新操作",false,e.getMessage());

    }
}
