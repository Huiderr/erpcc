package com.cwca.worship.manager.entity;

import com.cwca.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/30 - 16:34
 * @description ：用户表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicUpdate
@Table(name = "lot_user",indexes = {@Index(name = "lot_user_code",  columnList="userCode")})
@org.hibernate.annotations.Table(appliesTo = "lot_user",comment="用户信息表")
public class LotUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2853113983886808059L;

    @Column(unique=true,nullable = false,columnDefinition = "varchar(256) comment '用户编码'")
    private String userCode;

    @Column(nullable = false,columnDefinition = "varchar(512) comment '用户名称'")
    private String userName;

    @Column(nullable = false,columnDefinition = "varchar(256) comment '用户密码'")
    private String password;

    @Column(columnDefinition = "varchar(64) comment '联系电话'")
    private String phone;

    @Column(columnDefinition = "varchar(1024) comment '地址'")
    private String address;

    @OneToOne(fetch = FetchType.LAZY,cascade=CascadeType.DETACH)
    @JoinColumn(name = "tradeCode",referencedColumnName = "code")
    private DmLotTrade dmLotTrade;

    @Column(nullable = false,columnDefinition = "varchar(64) default 'customer' comment '用户类型：customer,manager'")
    private String userType = "customer";

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "role_code",referencedColumnName = "code")
    @NotFound(action = NotFoundAction.IGNORE)
    private LotRole lotRole;

    @Column(columnDefinition = "char(1) default '0' comment '状态: 0(有效); 1(无效);...'")
    private String status = "0";

}
