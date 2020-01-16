package com.cwca.common.base;

import com.cwca.common.utils.DateUtils;
import lombok.Data;

import javax.persistence.*;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/30 - 16:20
 * @description ：实体类基类
 */
@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "int(32) comment '序号ID'")
    private int id;

    @Column(updatable=false, columnDefinition = "varchar(256)")
    private String creater;

    @Column(updatable=false,columnDefinition = "varchar(128)")
    private String createTime;

    @Column(insertable=false,columnDefinition = "varchar(256)")
    private String updater;

    @Column(insertable=false,columnDefinition = "varchar(128)")
    private String updateTime;

    @Column(columnDefinition = "varchar(256)")
    private String remark;

    @PrePersist
    protected void onCreate() {
        createTime = DateUtils.getDateTime(DateUtils.DATE_FORMAT_DEFAULT);
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = DateUtils.getDateTime(DateUtils.DATE_FORMAT_DEFAULT);
    }

}
