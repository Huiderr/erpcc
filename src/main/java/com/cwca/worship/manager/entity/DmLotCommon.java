package com.cwca.worship.manager.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/30 - 16:34
 * @description ：代码表表
 */
@Data
public class DmLotCommon implements Serializable {

    private static final long serialVersionUID = 2853113983886808059L;

    private String id;

    private String tableName;

    private String code;

    private String description;

    private String remark;

    private String status = "0";

    private String creater;

    private String createTime;

    private String updater;

    private String updateTime;

}