package com.keep.wxoa.common;


/**
 * 自定义业务异常类
 * @Author: xinyueyan
 * @Date: 12/7/2020 4:25 PM
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 5835074336795624099L;
    /**
     * 错误码
     */
    private String code;
    /**
     * 错误信息
     */
    private String message;

    public BizException() {
        super();
    }


    public BizException(String code) {
        super();
        this.code = code;
    }
    public BizException(String code,String message) {
        super();
        this.code = code;
        this.message=message;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
