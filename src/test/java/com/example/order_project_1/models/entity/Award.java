package com.example.order_project_1.models.entity;

import com.example.order_project_1.models.entity.base.BaseEntity;
import gaarason.database.annotation.Column;
import gaarason.database.annotation.Primary;
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
@Table(name = "award")
public class Award extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** auto generator start **/


    @Primary(increment = false)
    @Column(name = "awarId", length = 30L)
    private String awarid;

    @Column(name = "description", nullable = true, length = 39L)
    private String description;

    @Column(name = "awardPrize", nullable = true, length = 30L)
    private String awardprize;

    @Column(name = "awardTime", nullable = true)
    private LocalDate awardtime;

    @Column(name = "createAt", nullable = true)
    private LocalDateTime createat;

    @Column(name = "updateAT", nullable = true)
    private LocalDateTime updateat;


    /** auto generator end **/

    @Repository
    public static class Model extends BaseEntity.BaseModel<Award, String> {

    }

}