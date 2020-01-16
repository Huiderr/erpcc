package com.cwca.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * md5加密与验证工具类
 *
 * @author wongs
 */
public class MD5Utils {

    private static final String MD5_KEY = "CWCA_SECURITY";

    /**
     * MD5方法
     *
     * @param text 明文
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text) {
        //加密后的字符串
        String encodeStr = DigestUtils.md5Hex(text + MD5_KEY);
        return encodeStr;
    }

    public static String md5Double(String text) {
        //加密后的字符串
        String encodeStr = DigestUtils.md5Hex(text + MD5_KEY);
        String result = DigestUtils.md5Hex(encodeStr);
        return result;
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param md5  密文
     * @return true/false
     */
    public static boolean verify(String text, String md5) {
        //根据传入的密钥进行验证
        String md5Text = md5(text);
        if (md5Text.equalsIgnoreCase(md5)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String string1 = "nxhtxx-zzbs";
        System.out.println(md5Double(string1));
    }
}
