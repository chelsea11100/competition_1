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
@Table(name = "award_work_competition")
public class AwardWorkCompetition extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** auto generator start **/


    @Column(name = "awardId", nullable = true, length = 30L)
    private String awardid;

    @Column(name = "competitionId", nullable = true, length = 40L)
    private String competitionid;

    @Column(name = "workId", nullable = true, length = 40L)
    private String workid;


    /** auto generator end **/

    @Repository
    public static class Model extends BaseModel<AwardWorkCompetition, String> {

    }

}