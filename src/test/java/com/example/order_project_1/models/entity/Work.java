package com.example.order_project_1.models.entity;

import com.example.order_project_1.models.entity.base.BaseEntity;
import gaarason.database.annotation.Column;
import gaarason.database.annotation.Primary;
import gaarason.database.annotation.Table;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;


@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "work")
public class Work extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** auto generator start **/


    @Primary(increment = false)
    @Column(name = "workId", length = 30L)
    private String workid;

    @Column(name = "workName", nullable = true, length = 30L)
    private String workname;

    @Column(name = "competitionId", nullable = true, length = 40L)
    private String competitionid;

    @Column(name = "status", nullable = true)
    private Integer status;

    @Column(name = "description", nullable = true, length = 100L)
    private String description;

    @Column(name = "category", nullable = true, length = 39L)
    private String category;

    @Column(name = "teachStack", nullable = true, length = 39L)
    private String teachstack;

    @Column(name = "date", nullable = true)
    private LocalDate date;


    /** auto generator end **/

    @Repository
    public static class Model extends BaseEntity.BaseModel<Work, String> {

    }

}