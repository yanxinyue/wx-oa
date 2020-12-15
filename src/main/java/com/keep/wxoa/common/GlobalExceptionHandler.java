package com.keep.wxoa.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * 全局异常处理类
 * @Author: xinyueyan
 * @Date: 12/7/2020 2:34 PM
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**-------- 通用异常处理方法 --------**/
    @ExceptionHandler(Exception.class)
    public ResponseMsg error(Exception e) {
        e.printStackTrace();
        return ResponseMsg.fail();
    }

    /**-------- 指定异常处理方法 --------**/
    @ExceptionHandler(NullPointerException.class)
    public ResponseMsg error(NullPointerException e) {
        e.printStackTrace();
        return ResponseMsg.fail("空指针");
    }

    /**-------- 自定义定异常处理方法 --------**/
    @ExceptionHandler(BizException.class)
    public ResponseMsg error(BizException e) {
        log.error("出现错误{},",e);
        HashMap<String,String> errdata = new HashMap<>();
        errdata.put("code",e.getCode());
        errdata.put("message",e.getMessage());
        return ResponseMsg.fail("",errdata);
    }

}
