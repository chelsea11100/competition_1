package com.example.competition_1.services;

import com.example.competition_1.models.entity.*;
import org.springframework.stereotype.Service;
import gaarason.database.contract.eloquent.RecordList;
import gaarason.database.contract.eloquent.Record;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AwardService {

    private final Work.Model workModel;
    private final WorkUser.Model workUserModel;
    private final Competitions.Model competitionsModel;

    private final AwardWorkCompetition.Model awardWorkCompetitionModel;

    private final Award.Model awardModel;

    public AwardService(Work.Model workModel, WorkUser.Model workUserModel, Competitions.Model competitionsModel, AwardWorkCompetition.Model awardWorkCompetitionModel, Award.Model awardModel) {
        this.workModel = workModel;
        this.workUserModel = workUserModel;
        this.competitionsModel = competitionsModel;
        this.awardWorkCompetitionModel = awardWorkCompetitionModel;
        this.awardModel = awardModel;
    }


    public boolean addAward(Award award, String competitionName, String workName) {
        try {
            // 设置创建时间
            award.setCreateat(LocalDateTime.now());

            // 生成唯一的 awardId
            award.setAwarid(UUID.randomUUID().toString());

            // 插入数据到 Award 表
            awardModel.newQuery().insert(award);
            System.out.println("成功插入数据，ID: " + award.getAwarid());

            // 根据 competitionName 查询 competitionId
            String competitionId = null;
            RecordList<Competitions, ?> competitionRecords = competitionsModel.newQuery()
                    .where("competitionName", competitionName)
                    .get();
            List<Competitions> competitions = competitionRecords.stream()
                    .map(Record::getEntity)
                    .collect(Collectors.toList());
            if (competitions.isEmpty()) {
                System.out.println("未找到对应的 competition 记录");
                return false;
            }
            competitionId = competitions.get(0).getId();

            // 根据 workName 查询 workId
            String workId = null;
            RecordList<Work, ?> workRecords = workModel.newQuery()
                    .where("workName", workName)
                    .get();
            List<Work> works = workRecords.stream()
                    .map(Record::getEntity)
                    .collect(Collectors.toList());
            if (works.isEmpty()) {
                System.out.println("未找到对应的 work 记录");
                return false;
            }
            workId = works.get(0).getWorkid();

            // 生成唯一的 id
            String id = UUID.randomUUID().toString();

            // 创建 AwardWorkCompetition 对象
            AwardWorkCompetition awardWorkCompetition = new AwardWorkCompetition();
            awardWorkCompetition.setWorkid(workId);
            awardWorkCompetition.setCompetitionid(competitionId);
            awardWorkCompetition.setAwardid(award.getAwarid());
            awardWorkCompetition.setId(id);

            // 插入数据到 AwardWorkCompetition 表
            awardWorkCompetitionModel.newQuery().insert(awardWorkCompetition);
            System.out.println("成功插入数据到 award_work_competition 表，ID: " + id);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("插入数据时发生异常：" + e.getMessage());
            return false;
        }
    }
    public boolean updateAward(String awarId, Award updatedAward) {
        try {
            updatedAward.setUpdateat(LocalDateTime.now());

            updatedAward.setAwarid(awarId);

            int updatedCount = awardModel.newQuery()
                    .where("awarId", awarId)
                    .update(updatedAward);

            return updatedCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("更新数据时发生异常：" + e.getMessage());
            return false;
        }
    }

    // 删除获奖信息
    public boolean deleteAward(String awarId) {
        try {
            // 通过 awardId 在 award_work_competition 表中查找 workId
            RecordList<AwardWorkCompetition, ?> awardWorkCompetitionRecords = awardWorkCompetitionModel.newQuery()
                    .where("awardId", awarId)
                    .get();
            List<AwardWorkCompetition> awardWorkCompetitions = awardWorkCompetitionRecords.stream()
                    .map(Record::getEntity)
                    .collect(Collectors.toList());

            if (awardWorkCompetitions.isEmpty()) {
                System.out.println("未找到对应的 award_work_competition 记录");
                return false;
            }

            String workId = awardWorkCompetitions.get(0).getWorkid();

            // 根据 workId 查找 work 表中的记录
            RecordList<Work, ?> workRecords = workModel.newQuery()
                    .where("workId", workId)
                    .get();
            List<Work> works = workRecords.stream()
                    .map(Record::getEntity)
                    .collect(Collectors.toList());

            if (works.isEmpty()) {
                System.out.println("未找到对应的 work 记录");
                return false;
            }

            Work work = works.get(0);
            // 将 work 记录的 status 字段修改为 2
            work.setStatus(2);
            workModel.newQuery().update(work);

            System.out.println("成功将 work 记录的 status 修改为 2，workId: " + workId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("修改 work 记录的 status 时发生异常：" + e.getMessage());
            return false;
        }
    }

    // 查看所有获奖记录
    public Map<String, Object> getAllAwards() {
        Map<String, Object> result = new HashMap<>();

        // 查询所有 Award 记录
        RecordList<Award, ?> awardRecords = awardModel.newQuery().get();
        List<Award> awards = awardRecords.stream()
                .map(Record::getEntity)
                .collect(Collectors.toList());
        result.put("awards", awards);

        List<Work> allWorks = new ArrayList<>();
        List<WorkUser> allWorkUsers = new ArrayList<>();
        Map<String, String> competitionNameMap = new HashMap<>();

        for (Award award : awards) {
            String awardId = award.getAwarid();
            if (awardId != null) {
                // 通过 awardId 查询 award_work_competition 表获取 workId
                RecordList<AwardWorkCompetition, ?> awardWorkCompetitionRecords = awardWorkCompetitionModel.newQuery()
                        .where("awardId", awardId)
                        .get();
                List<String> workIds = awardWorkCompetitionRecords.stream()
                        .map(Record::getEntity)

                        .map(AwardWorkCompetition::getWorkid)
                        .collect(Collectors.toList());

                for (String workId : workIds) {
                    // 通过 workId 查询 work 表
                    RecordList<Work, ?> workRecords = workModel.newQuery()
                            .where("workId", workId)
                            .get();
                    List<Work> works = workRecords.stream()
                            .map(Record::getEntity)
                            .collect(Collectors.toList());
                    allWorks.addAll(works);

                    // 通过 workId 查询 work_user 表
                    RecordList<WorkUser, ?> workUserRecords = workUserModel.newQuery()
                            .where("workId", workId)
                            .get();
                    List<WorkUser> workUsers = workUserRecords.stream()
                            .map(Record::getEntity)
                            .collect(Collectors.toList());
                    allWorkUsers.addAll(workUsers);

                    for (Work work : works) {
                        String competitionId = work.getCompetitionid();
                        // 通过 competitionId 查询 competition 表获取 competitionName
                        RecordList<Competitions, ?> competitionRecords = competitionsModel.newQuery()
                                .where("competitionId", competitionId)
                                .get();
                        List<Competitions> competitions = competitionRecords.stream()
                                .map(Record::getEntity)
                                .collect(Collectors.toList());
                        if (!competitions.isEmpty()) {
                            competitionNameMap.put(competitionId, competitions.get(0).getName());
                        }
                    }
                }
            }
        }

        result.put("works", allWorks);
        result.put("workUsers", allWorkUsers);
        result.put("competitionNameMap", competitionNameMap);

        return result;
    }
}