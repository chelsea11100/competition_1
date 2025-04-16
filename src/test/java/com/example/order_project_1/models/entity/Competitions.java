package com.example.order_project_1.models.entity;

import com.example.order_project_1.models.entity.base.BaseEntity;
import gaarason.database.annotation.Column;
import gaarason.database.annotation.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import org.springframework.stereotype.Repository;


@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "competitions")
public class Competitions extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** auto generator start **/


    @Column(name = "month", comment = "竞赛的存在的月份")
    private Integer month;

    @Column(name = "name", comment = "表示竞赛的名字")
    private String name;

    @Column(name = "category", length = 100L, comment = "表示竞赛的类别")
    private String category;

    @Column(name = "level", length = 100L, comment = "表示竞赛的等级")
    private String level;

    @Column(name = "location", nullable = true, length = 100L)
    private String location;

    @Column(name = "beginDate", nullable = true, length = 300L, comment = "表示竞赛的开始的日期")
    private String begindate;

    @Column(name = "endDate", nullable = true, length = 300L, comment = "表示竞赛的结束的日期")
    private String enddate;

    @Column(name = "date", nullable = true, comment = "竞赛的比赛的时间")
    private LocalDate date;

    @Column(name = "organizer", nullable = true, comment = "比赛的组织的部分")
    private String organizer;

    @Column(name = "requirements", nullable = true, length = 65535L, comment = "比赛的参与人群")
    private String requirements;

    @Column(name = "description", nullable = true, length = 65535L, comment = "表示竞赛的简单的概述")
    private String description;

    @Column(name = "contact", nullable = true, comment = "比赛的联系方式")
    private String contact;

    @Column(name = "website", nullable = true, comment = "比赛的网址")
    private String website;

    @Column(name = "created_at", nullable = true, comment = "记录创建时间")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true, comment = "记录更新时间")
    private LocalDateTime updatedAt;

    @Column(name = "prizes", nullable = true, length = 300L)
    private String prizes;

    @Column(name = "zhuangtai", nullable = true)
    private Integer zhuangtai;


    /** auto generator end **/

    @Repository
    public static class Model extends BaseEntity.BaseModel<Competitions, Integer> {

    }

}