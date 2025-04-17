package com.example.competition_1.models.entity;

import com.example.competition_1.models.entity.base.BaseEntity;
import gaarason.database.annotation.Column;
import gaarason.database.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;


@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "work_user")
public class WorkUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** auto generator start **/
    @Column(name = "id", nullable = true, length = 50L)
    private String id;

    @Column(name = "workId", nullable = true, length = 50L)
    private String workid;

    @Column(name = "userId", nullable = true, length = 30L)
    private String userid;

    @Column(name = "role", nullable = true, length = 30L)
    private String role;

    @Column(name = "type", nullable = true, length = 30L)
    private String type;


    /** auto generator end **/

    @Repository
    public static class Model extends BaseModel<WorkUser, String> {

    }

}