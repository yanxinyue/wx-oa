package com.keep.wxoa.common;

/**
 * 业务错误码枚举类
 * @Author: xinyueyan
 * @Date: 12/7/2020 5:35 PM
 */
public enum BizErrCode {
    /**
     * 错误代码
     */
    NULL_OBJ("K001","对象为空"),
    ERROR_ADD_USER("K002","添加用户失败"),
    UNKNOWN_ERROR("K003","系统繁忙，请稍后再试....");

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    BizErrCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取对应的desc
     * @param code
     * @return
     */
    public String getDescByCode(String code){
        for (BizErrCode err:values()) {
            if(err.getCode().equals(code)){
                return err.getDesc();
            }
        }
        return null;
    }


    /**
     * 重写toString方法，便于展示错误信息
     * @return
     */
    @Override
    public String toString() {
        return "错误码：" + code  + "->错误信息：" + desc ;
    }
}
