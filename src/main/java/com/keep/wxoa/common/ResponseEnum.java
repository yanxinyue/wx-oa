package com.keep.wxoa.common;

/**
 * 返回值结果枚举类
 * @Author: xinyueyan
 * @Date: 12/7/2020 9:35 AM
 */
public enum ResponseEnum {
    /**
     * 请求状态码
     */
    SUCCESS("1","成功"),
    FAIL("0","失败");

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
