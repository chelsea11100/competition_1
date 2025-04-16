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


@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "user")
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** auto generator start **/


    @Primary(increment = false)
    @Column(name = "userId", length = 30L)
    private String userid;

    @Column(name = "userName", nullable = true, length = 30L)
    private String username;

    @Column(name = "gender", nullable = true, length = 1L)
    private String gender;

    @Column(name = "professional", nullable = true, length = 40L)
    private String professional;

    @Column(name = "department", nullable = true, length = 30L)
    private String department;

    @Column(name = "grade", nullable = true, length = 20L)
    private String grade;

    @Column(name = "phoneNumber", nullable = true, length = 30L)
    private String phonenumber;

    @Column(name = "password", nullable = true, length = 30L)
    private String password;


    /** auto generator end **/

    @Repository
    public static class Model extends BaseModel<User, String> {

    }

}