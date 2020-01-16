package com.cwca.worship.manager.entity;

import com.cwca.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/30 - 16:34
 * @description ：代码表表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicUpdate
@Table(name = "lot_route")
@org.hibernate.annotations.Table(appliesTo = "lot_route",comment="权限路由表")
public class LotRoute extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2853113983886808059L;

    @Column(columnDefinition = "int(32) comment '父节点ID'")
    private int pid;

    @Column(nullable = false,columnDefinition = "varchar(128) comment '路由编码'")
    private String code;

    @Column(nullable = false,columnDefinition = "varchar(256) comment '路由名称'")
    private String business;

    @Column(columnDefinition = "varchar(1024) comment '路由说明'")
    private String description;

    @Column(columnDefinition = "varchar(256) comment '路由链接'")
    private String routeUrl;

    @Column(columnDefinition = "varchar(128) comment '路由图标'")
    private String icon;

    @Column(nullable = false,columnDefinition = "varchar(64) default 'customer' comment '路由类型：customer,manager'")
    private String routeType;

    @Column(nullable = false,columnDefinition = "char(1) default '0' comment '状态: 0(有效); 1(无效); ...'")
    private String status = "0";

    @ManyToMany(mappedBy = "lotRoute",fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    private List<LotRole> lotRoles;

    @Transient
    private List<LotRoute> nodes = new ArrayList<>();
}
