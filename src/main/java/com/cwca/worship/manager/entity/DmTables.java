package com.cwca.worship.manager.entity;

import com.cwca.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/30 - 16:34
 * @description ：代码表表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicUpdate
@Table(name = "dm_tables")
@org.hibernate.annotations.Table(appliesTo = "dm_tables",comment="代码信息表")
public class DmTables extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2853113983886808059L;

    @Column(nullable = false,columnDefinition = "varchar(256) comment 'DM表编码'")
    private String dmTableCode;

    @Column(nullable = false,columnDefinition = "varchar(256) comment 'DM表名称'")
    private String dmTableName;

    @Column(nullable = false,columnDefinition = "char(1) default '0' comment '状态: 0(有效); 1(无效); ...'")
    private String status = "0";

}
