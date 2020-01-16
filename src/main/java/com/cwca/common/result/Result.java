package com.cwca.common.result;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ Author     ：wongs.
 * @ Date       ：Created in 2018/12/1 - 13:07
 * @ Description：统一返回结果
 */
@Getter
@ToString
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 4055836286846897080L;
    /**
     * 结果类型
     */
    private T data;
    /**
     * 结果代码
     */
    private int code;
    /**
     * 结果信息
     */
    private String msg;

    public Result() {
        this.code = Message.SUCCESS;
        this.msg = Message.getMessage(code);
    }

    public Result(T data) {
        this.code = Message.SUCCESS;
        this.msg = Message.getMessage(code);
        this.data = data;
    }

    public Result(int code) {
        this.code = code;
        this.msg = Message.getMessage(code);
    }

    public Result(int code, T data) {
        this.code = code;
        this.msg = Message.getMessage(code);
        this.data = data;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 增加自定义信息
     */
    public void addMessage(String message) {
        this.msg = this.msg + " [" + message + "]";
    }
}
