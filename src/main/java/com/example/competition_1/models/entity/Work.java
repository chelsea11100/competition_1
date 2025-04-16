package com.example.competition_1.models.entity;

import com.example.competition_1.models.entity.base.BaseEntity;
import gaarason.database.annotation.Column;
import gaarason.database.annotation.Primary;
import gaarason.database.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


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


    private List<WorkUser> workUsers;

    public void setWorkUsers(List<WorkUser> workUsers) {
        this.workUsers = workUsers;
    }
    /** auto generator end **/

    @Repository
    public static class Model extends BaseModel<Work, String> {

    }

}