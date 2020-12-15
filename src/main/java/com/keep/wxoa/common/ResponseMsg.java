package com.keep.wxoa.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装返回体
 * @Author: xinyueyan
 * @Date: 12/6/2020 6:32 PM
 * @JsonInclude(JsonInclude.Include.NON_NULL)

 */
@Data
public class ResponseMsg implements Serializable{

    private static final long serialVersionUID = -7993773805389428661L;
    /**
     * 请求代码：请求成功失败
     */
    private String code;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 实体数据
     */
    private Object data;

    /**
     * 成功时返回
     * @param
     * @return
     */
    public static  ResponseMsg success(){
        return new ResponseMsg(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getMsg());
    }
    public static  ResponseMsg success(String msg){
        return new ResponseMsg(ResponseEnum.SUCCESS.getCode(),msg);
    }
    public static  ResponseMsg success(String msg,Object data){
        return new ResponseMsg(ResponseEnum.SUCCESS.getCode(),msg,data);
    }
    public static  ResponseMsg success(Object data){
        return new ResponseMsg(ResponseEnum.SUCCESS.getCode(),data);
    }

    /**
     * 失败时返回
     * @param
     * @return
     */
    public static  ResponseMsg fail(){
        return new ResponseMsg(ResponseEnum.FAIL.getCode(),ResponseEnum.FAIL.getMsg());
    }
    public static  ResponseMsg fail(String msg){
        return new ResponseMsg(ResponseEnum.FAIL.getCode(),msg);
    }
    public static  ResponseMsg fail(String msg,Object data){
        return new ResponseMsg(ResponseEnum.FAIL.getCode(),msg,data);
    }
    public static  ResponseMsg fail(Object data){
        return new ResponseMsg(ResponseEnum.FAIL.getCode(),data);
    }


    public ResponseMsg() {
    }

    public ResponseMsg(String code) {
        this.code = code;
    }

    public ResponseMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseMsg(String code, Object data) {
        this.code = code;
        this.data = data;
    }

    public ResponseMsg(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


}
