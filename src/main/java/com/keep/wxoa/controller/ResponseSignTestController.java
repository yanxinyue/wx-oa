package com.keep.wxoa.controller;

import com.alibaba.fastjson.JSONObject;
import com.keep.wxoa.util.RsaSignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 验签测试类
 * @author yanxinyue
 */
@RestController
@Slf4j
public class ResponseSignTestController extends BaseController  {
    @PostMapping("/returnMsg")
    public String  returnMsg(String message) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        log.info("接受请求内容为：{}", jsonObject.toJSONString());
        String sign = jsonObject.getString("sign");
        jsonObject.remove("sign");
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJZEsC3+XW/SgF6aG94TE0NgY0CCv9FNDhTBHc9PKGHA\n" +
                "1wsEGpXrMZJsv3XyRjsY2qExnrt+0f4LKqfE5b1XuzMCAwEAAQ==";
        boolean flag = RsaSignUtil.verify(jsonObject, publicKey, sign);
        JSONObject object = new JSONObject();
        if(flag) {
            object.put("code", "200");
            object.put("status", "success");
            object.put("message:", "验签成功");
        }else {
            object.put("code", "400");
            object.put("status", "failure");
            object.put("message:", "验签失败");
        }
        return object.toJSONString();
    }
}
