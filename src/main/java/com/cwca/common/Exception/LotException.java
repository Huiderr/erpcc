package com.cwca.common.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：wongs.
 * @date ：Created in 2019/11/15 - 10:44
 * @description ：**
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LotException extends RuntimeException{

    private static final long serialVersionUID = 5074628297197990021L;

    private Integer code;

    public LotException(String message) {
        super(message);
    }

    public LotException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
