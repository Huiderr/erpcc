package com.cwca.worship.manager.entity;

import com.cwca.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
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
@Table(name = "lot_role")
@org.hibernate.annotations.Table(appliesTo = "lot_role",comment="角色表")
public class LotRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8507089392724232002L;
    @Column(nullable = false,columnDefinition = "varchar(128) comment '角色编码'")
    private String code;

    @Column(nullable = false,columnDefinition = "varchar(256) comment '角色名称'")
    private String business;

    @Column(columnDefinition = "varchar(1024) comment '角色说明'")
    private String description;

    @Column(nullable = false,columnDefinition = "varchar(64) default 'customer' comment '角色类型：customer,manager'")
    private String roleType;

    @Column(nullable = false,columnDefinition = "char(1) default '0' comment '状态: 0(有效); 1(无效); ...'")
    private String status = "0";

    @JsonIgnore
    @OneToMany(mappedBy = "lotRole",fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<LotUser> userList;

    @JsonIgnore
    @JoinTable(name = "lot_role_route",
            joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "route_id",referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    private List<LotRoute> lotRoute;
}
