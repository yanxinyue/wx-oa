package com.keep.wxoa.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA工具类
 * 请求类中使用私钥加签，响应类中使用公钥验签。
 *
 * @author yanxinyue
 * @date 2020
 */
@Slf4j
//@Component
public class RsaSignUtil {
    /**
     * 摘要算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 签名算法
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 公钥私钥
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    private static final String CHARSET = "UTF-8";
    public static final Integer RSA_KEY_SIZE = 512;

    /**
     * 加签方法
     *
     * @function: 使用字符串格式的私钥为JSONObject格式的内容加签
     * @param: [jsonObject, privateKey]
     */
    public static String sign(String data, String privateKeyStr) {
        String signMsg = "";
        log.info("加签对象内容为：{}", data);
        try {
            //根据摘要生成算法，初始化签名对象：algorithm参数可以取SHA256WithRSA或者MD5WithRSA等参数
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            //把私钥字符串转化成私钥对象
            PrivateKey privateKey = getPrivateKey(privateKeyStr);
            //由私钥初始化加签对象
            signature.initSign(privateKey);
            //把原始报文更新到加签对象
            signature.update(data.getBytes(CHARSET));
            //正式加签
            byte[] signBytes = signature.sign();
            signMsg = Base64.encodeBase64String(signBytes);
        } catch (Exception e) {
            log.error("加签失败{},", e);
        }
        return signMsg;
    }

    /**
     * 验签方法
     *
     * @function: 使用字符串格式的公钥为JSONObject格式的内容验签
     */
    public static boolean verify(JSONObject jsonObject, String publicKeyStr, String sign) {
        String s = jsonObject.toJSONString();
        log.info("s:{}", s);
        boolean verified = false;
        try {
            //初始化
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            PublicKey publicKey = getPublicKey(publicKeyStr);
            //用公钥初始化验签对象
            signature.initVerify(publicKey);
            //把原始报文更新到验签对象
            signature.update(s.getBytes(CHARSET));
            //验签
            byte[] signBytes = Base64.decodeBase64(sign.getBytes());
            return signature.verify(signBytes);
        } catch (Exception e) {
            log.error("验签失败{},", e);
            return verified;
        }
    }

    /**
     * @function: 获取PublicKey格式的公钥
     */
    public static PublicKey getPublicKey(String key) {
        PublicKey publicKey = null;
        try {
            byte[] keyBytes = decryptBASE64(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            // RSA对称加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 取公钥匙对象
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }


    /**
     * @function: 获取PrivateKey格式的私钥
     */
    public static PrivateKey getPrivateKey(String key) {
        PrivateKey privateKey = null;
        try {
            //根据签名算法初始化KeyFactory：签名加签用的是RSA算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            byte[] keyBytes = decryptBASE64(key);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * @function: 初始化公钥和私钥
     */
    public static Map<String, Object> initKey() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGen.initialize(RSA_KEY_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    //获得公钥字符串
    public static String getPublicKeyStr(Map<String, Object> keyMap) {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //获得私钥字符串
    public static String getPrivateKeyStr(Map<String, Object> keyMap) {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //编码返回字符串
    public static String encryptBASE64(byte[] key) {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    //解码返回byte
    public static byte[] decryptBASE64(String key) {
        byte[] bytes = null;
        try {
            return (new BASE64Decoder()).decodeBuffer(key);
        } catch (IOException e) {
            return bytes;
        }
    }

    public static void main(String[] args) {
        Map<String, Object> keyMap = initKey();
        String publicKey = getPublicKeyStr(keyMap);
        log.info("JAVA生成RSA公钥：{}", publicKey);
        String privateKey = getPrivateKeyStr(keyMap);
        log.info("JAVA生成RSA私钥：{}", privateKey);
    }
}

