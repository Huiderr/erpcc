package com.cwca.common.result;

/**
 * @author      ：wongs.
 * @date        ：Created in 2018/12/1 - 13:06
 * @description ：状态码定义
 */
public class Message {

    //成功
    public static final int SUCCESS = 0;
    //失败
    public static final int FAILURE = 9;

    public static String[] message;

    public final class Login {
        //登陆系统错误码 100-299
        public static final int FAILURE_LOGIN = 100;//登陆失败
        public static final int FAILURE_USER_NONE = 101;//用户不存在
        public static final int FAILURE_REG_PERSON = 102;//用户已存在

        //updateUserInfo error
        public static final int FAILURE_UPDATE_PWDEQUAL = 103;//新密码和原密码不可重复
        public static final int FAILURE_UPDATE_PWDERR = 104;//原密码错误

        public static final int EXCEPTION_AUTHOR_NONE = 105;//未知错误

        public static final int EXCEPTION_SERVER_ANGRY = 500;//服务器闹脾气，请稍后再试

    }

    static {
        message = new String[4999];
        //正确
        message[SUCCESS] = "成功";
        message[FAILURE] = "失败";
        //登陆系统
        message[Login.FAILURE_LOGIN] = "登陆失败";
        message[Login.FAILURE_USER_NONE] = "用户不存在";
        message[Login.FAILURE_REG_PERSON] = "用户已存在";
        message[Login.FAILURE_UPDATE_PWDEQUAL] = "新密码和原密码不可重复";
        message[Login.FAILURE_UPDATE_PWDERR] = "原密码错误";
        message[Login.EXCEPTION_AUTHOR_NONE] = "未知错误";

        message[Login.EXCEPTION_SERVER_ANGRY] = "服务器闹脾气，请稍后再试";

    }

    public static String getMessage(int code) {
        if (message[code] == null) {
            return "未知错误";
        }
        return message[code];
    }
}
