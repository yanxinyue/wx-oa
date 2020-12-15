package com.keep.wxoa.util;

import okhttp3.*;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: xinyueyan
 * @Date: 12/10/2020 4:16 PM
 */
public class SignUtil {
    private static final String OT_SECRET="1zA3iMuf-c6HMUUhI-o7KkxHJ4-uK3uWl6u";
    private static final String OT_KEY=   "n9QHRQeA-gskepsyd-0krCS7cD-Ck4EHc9g";
    private static final String VERB_POST="POST";
    private static final String VERB_GET="GET";
    private static String hostUrl="localhost:9002";
    public static OkHttpClient client=new OkHttpClient();


    //测试
    public static void main(String[] args) {
        //        HashMap<String,String> map = new HashMap<String,String>();
////        map.put("username","lixu1");
////        map.put("phone","15940525612");
////        try {
////            Response r = api_call("POST", "/premium/member", null, JSONObject.toJSONString(map),0,"");
////            System.out.println(r.body().toString());
////        } catch (Exception e) {
////            e.printStackTrace();
////        }



        String a=gen_sign(OT_SECRET,"GET","/okex/account1/info","1562050216094",null);
        System.out.println(a);

        /*String ss ="&company_member=user1@lddtests";
        System.out.println(ss.substring(1,ss.length()-1));*/
    }

    public static String sha256_HMAC(String secret, String message) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            String hashs = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
            System.out.println("hashs:"+hashs);
            hash = byteArrayToHexString(bytes);
            System.out.println(hash);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }


    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1){
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }



    public static String gen_nonce(){
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    //获取签名
    public static String gen_sign(String secret, String verb, String endpoint, String nonce, String data_str){

        if (data_str == null){
            data_str = "";
        }

        String message = verb + endpoint + nonce + data_str;
        //私钥加签
        String signature = sha256_HMAC(secret, message);

        return signature;
    }

    /**
     *
     * @param method :调用方式POST/GET
     * @param endpoint：访问的url  如：/test/get1
     * @param params:参数map
     * @param jsonData
     * @return
     * @throws IOException
     */
    public static Response api_call(String method, String endpoint, HashMap<String, String> params, String jsonData) throws IOException {

        //请求方式大写
        method = method.toUpperCase();
        //生成随机数
        String nonce = gen_nonce();
        String url = hostUrl + endpoint;
        //生成签名：secret，访问方式，路径，随机数，参数json串
        String sign = gen_sign(OT_SECRET, method, endpoint, nonce, jsonData);
        String params_str = "";

        if (params != null){
            for (Map.Entry<String,String> entry : params.entrySet()){
                params_str += "&"+ entry.getKey() + "=" + entry.getValue();
            }
            params_str = "?" + params_str.substring(1,params_str.length());
        }

        url = url + params_str;

        //设置请求体
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(mediaType,jsonData);

        Request request = new Request.Builder().url(url).method(method,method.equals("GET") ? null :requestBody).
                addHeader("Api-Nonce", nonce).
                addHeader("Api-Key", OT_KEY).
                addHeader("Api-Signature", sign).
                addHeader("Content-Type", "application/json").
                build();

        Response response = client.newCall(request).execute();
        return response;
    }



}
