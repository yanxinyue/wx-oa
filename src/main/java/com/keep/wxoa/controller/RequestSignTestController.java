package com.keep.wxoa.controller;

import com.alibaba.fastjson.JSONObject;
import com.keep.wxoa.util.RsaSignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *请求加签测试
 *
 * @author yanxinyue
 */
@RestController
@RequestMapping(value="/sign")
@Slf4j
public class RequestSignTestController extends BaseController {

    @Autowired
    private ResponseSignTestController responseController;

    @PostMapping("/getJsonObject")
    private String getJsonObject(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("signType", "RSA");
        jsonObject.put("enterpriseId", "201975538583911110");
        jsonObject.put("nonce", "b0eed33073664f5fa983c5b774dbd4b6");
        jsonObject.put("timestamp", "2019-12-07 01:19:25");
        Map<String, Object> map = new HashMap<>();
        map.put("bankCode", "其他");
        map.put("batchNo", "201975538583911110b1084fa29f6c");
        map.put("bankCardNumber", "6217856100077026406");
        map.put("paymentNote", "佣金发放");
        map.put("idCardNumber", "320123199103104650");

        jsonObject.put("data", map);
        //私钥内容
        String privateKey = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAlkSwLf5db9KAXpob3hMTQ2BjQIK/\n" +
                "0U0OFMEdz08oYcDXCwQalesxkmy/dfJGOxjaoTGeu37R/gsqp8TlvVe7MwIDAQABAkAV3f8JYme0\n" +
                "exyFJ8YAZiQ6fM9UtvI1AhKXgJbW8Tz+JHh9JS20PfNG5cPg46usOSUWOt5aqLmj2pa0BxKGmbfx\n" +
                "AiEA80Ej7lD27VNB+v5FxyJIjAlftKJY07SYM1vtLOGYUBkCIQCeJEqOGaOd5+ZgwXMy5Jv7Zabl\n" +
                "hVKm2COvJy5vuHFfKwIgO//0QeMclPs1ShiP04dkR45EWgS1Tjq8U+a3wJtAe3kCIAZqjIfVbHkE\n" +
                "pqk4UBc5hUae6jWXwjG4xUHfAhhQUHE/AiA5kaYBD64ievda9GFtYw1H1KSkx2CShMpDDseZgfwb\n" +
                "Jg==";
        String data = jsonObject.toString();

        String sign = RsaSignUtil.sign(data, privateKey);
        log.info("签名sign为：{}", sign);
        jsonObject.put("sign", sign);
        String message = responseController.returnMsg(jsonObject.toJSONString());
        return message;
    }
}
