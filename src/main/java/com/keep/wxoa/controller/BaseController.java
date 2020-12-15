package com.keep.wxoa.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 基础控制类，所有controller 都要继承这个类
 */
public class BaseController {
    /**
     * json转换工具
     */
    protected final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
}