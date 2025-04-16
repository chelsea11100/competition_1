package com.example.order_project_1.models.entity;

import com.example.order_project_1.models.entity.base.BaseEntity;
import gaarason.database.annotation.Column;
import gaarason.database.annotation.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.Data;
import org.springframework.stereotype.Repository;


@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "work_user")
public class WorkUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** auto generator start **/


    @Column(name = "workId", nullable = true, length = 30L)
    private String workid;

    @Column(name = "userId", nullable = true, length = 30L)
    private String userid;

    @Column(name = "role", nullable = true, length = 30L)
    private String role;

    @Column(name = "type", nullable = true, length = 30L)
    private String type;


    /** auto generator end **/

    @Repository
    public static class Model extends BaseEntity.BaseModel<WorkUser, String> {

    }

}