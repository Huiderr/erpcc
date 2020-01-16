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
 * @description ：通知公告表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicUpdate
@Table(name = "lot_notice")
@org.hibernate.annotations.Table(appliesTo = "lot_notice",comment="通知公告表")
public class LotNotice extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8371449237450770905L;

    @Column(nullable = false,columnDefinition = "varchar(256) comment '所属行业（fk:dm_lot_trade:code）'")
    private String trade;

    @Column(nullable = false,columnDefinition = "varchar(16) comment '通知公告：公告(有效期内首页弹出公告)'")
    private String notice = "通知";

    @Column(nullable = false,columnDefinition = "varchar(1000) comment '公告标题'")
    private String title;

    @Column(nullable = false,columnDefinition = "varchar(10000) comment '内容'")
    private char[] content;

    @Column(nullable = false,columnDefinition = "varchar(16) comment '有效期起'")
    private String start;

    @Column(nullable = false,columnDefinition = "varchar(16) comment '有效期止'")
    private String end;

    @Column(nullable = false,columnDefinition = "char(1) default '0' comment '状态: 0(有效); 1(无效); ...'")
    private String status = "0";

}
