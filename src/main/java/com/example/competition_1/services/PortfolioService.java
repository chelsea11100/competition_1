package com.example.competition_1.services;

import com.example.competition_1.models.entity.Work;
import com.example.competition_1.models.entity.WorkUser;
import com.example.competition_1.models.entity.Competitions;
import gaarason.database.contract.eloquent.Record;
import gaarason.database.contract.eloquent.RecordList;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortfolioService {
    private final Work.Model workModel;
    private final WorkUser.Model workUserModel;
    private final Competitions.Model competitionsModel;

    public PortfolioService(Work.Model workModel, WorkUser.Model workUserModel, Competitions.Model competitionsModel) {
        this.workModel = workModel;
        this.workUserModel = workUserModel;
        this.competitionsModel = competitionsModel;
    }

    public Work addPortfolio(Work work, List<WorkUser> workUsers) {
        work.setDate(LocalDate.now());

        String competitionName = work.getWorkname();
        Competitions competition = competitionsModel.newQuery()
                .where("name", competitionName)
                .first()
                .getEntity();
        if (competition != null) {
            work.setCompetitionid(String.valueOf(competition.getId()));
        } else {
            throw new RuntimeException("未找到对应的竞赛信息");
        }

        String insertedWorkId = workModel.newQuery().insertGetId(work);
        if (insertedWorkId != null) {
            Work savedWork = workModel.newQuery().find(insertedWorkId).getEntity();
            if (savedWork != null) {
                for (WorkUser workUser : workUsers) {
                    workUser.setWorkid(savedWork.getWorkid());
                    String insertedWorkUserId = workUserModel.newQuery().insertGetId(workUser);
                    if (insertedWorkUserId == null) {
                        throw new RuntimeException("插入WorkUser记录失败");
                    }
                }
                return savedWork;
            }
        }
        return null;
    }
    public boolean deletePortfolio(String workId) {
        int deletedCount = workModel.newQuery()
                .where("workId", workId)
                .delete();
        return deletedCount > 0;
    }
    public Work updatePortfolio(String workId, Work updatedWork, List<WorkUser> workUsers) {
        int workUpdatedCount = workModel.newQuery()
                .where("workId", workId)
                .update(updatedWork);
        if (workUpdatedCount > 0) {
            if (workUsers != null &&!workUsers.isEmpty()) {
                workUserModel.newQuery()
                        .where("workId", workId)
                        .delete();

                for (WorkUser workUser : workUsers) {
                    workUser.setWorkid(workId);
                    String insertedWorkUserId = workUserModel.newQuery().insertGetId(workUser);
                    if (insertedWorkUserId == null) {
                        throw new RuntimeException("插入WorkUser记录失败");
                    }
                }
            }
            return workModel.newQuery().find(workId).getEntity();
        }
        return null;
    }
    public List<Work> getAllPortfolios() {
        RecordList<Work, ?> records = workModel.newQuery().get();
        List<Work> works = records.stream()
                .map(Record::getEntity)
                .collect(Collectors.toList());

        for (Work work : works) {
            String workId = work.getWorkid();
            RecordList<WorkUser, ?> userRecords = workUserModel.newQuery()
                    .where("workId", workId)
                    .get();
            List<WorkUser> workUsers = userRecords.stream()
                    .map(Record::getEntity)
                    .collect(Collectors.toList());
            work.setWorkUsers(workUsers);
        }

        return works;
    }

}