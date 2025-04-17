package com.example.competition_1.services;

import com.example.competition_1.DTO.WorkDTO;
import com.example.competition_1.models.entity.Work;
import com.example.competition_1.models.entity.WorkUser;
import com.example.competition_1.models.entity.Competitions;
import gaarason.database.contract.eloquent.Record;
import gaarason.database.contract.eloquent.RecordList;
import gaarason.database.exception.SQLRuntimeException;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
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


    public Work addPortfolio(WorkDTO workDTO) {
        System.out.println("开始处理 addPortfolio 方法");
        Work work = new Work();
        work.setWorkname(workDTO.getWorkname());
        work.setStatus(workDTO.getStatus());
        work.setDescription(workDTO.getDescription());
        work.setCategory(workDTO.getCategory());
        work.setTeachstack(workDTO.getTeachstack());
        work.setDate(LocalDate.now());

        String competitionName = workDTO.getCompetitionName();
        if (competitionName == null || competitionName.isEmpty()) {
            System.out.println("未提供竞赛名称");
            throw new RuntimeException("未提供竞赛名称");
        }

        System.out.println("根据竞赛名称查询对应的竞赛信息");
        Competitions competition = competitionsModel.newQuery()
                .where("name", competitionName)
                .first()
                .getEntity();
        if (competition != null) {
            work.setCompetitionid(String.valueOf(competition.getId()));
        } else {
            System.out.println("未找到对应的竞赛信息");
            throw new RuntimeException("未找到对应的竞赛信息");
        }

        try {
            // 生成唯一的 workId
            String workId = UUID.randomUUID().toString();
            work.setWorkid(workId);
            System.out.println("生成的 Work workId: " + workId + ", 长度: " + workId.length());

            // 插入 Work 记录
            System.out.println("开始插入 Work 记录");
            workModel.newQuery().insert(work);
            System.out.println("成功插入 Work 记录，ID: " + work.getWorkid());

            List<WorkUser> workUsers = workDTO.getWorkUsers();
            for (WorkUser workUser : workUsers) {
                // 生成唯一的 workUserId
                String workUserId = UUID.randomUUID().toString();
                workUser.setId(workUserId);
                // 使用前面插入 work 表生成的 workId
                workUser.setWorkid(workId);

                try {
                    // 插入 WorkUser 记录
                    System.out.println("开始插入 WorkUser 记录");
                    workUserModel.newQuery().insert(workUser);
                    System.out.println("成功插入 WorkUser 记录，ID: " + workUser.getId());
                } catch (SQLRuntimeException e) {
                    System.out.println("插入 WorkUser 记录时的 SQL 错误: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("插入 WorkUser 记录失败: " + e.getMessage());
                }
            }
            return work;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("插入数据时发生异常：" + e.getMessage());
            return null;
        }
    }

    public boolean deletePortfolio(String workId) {
        int deletedCount = workModel.newQuery()
                .where("workId", workId)
                .delete();
        return deletedCount > 0;
    }
    public Work updatePortfolio(String workId, WorkDTO workDTO) {
        try {
            // 检查要更新的 Work 是否存在
            Work existingWork = workModel.newQuery().find(workId).getEntity();
            if (existingWork == null) {
                throw new RuntimeException("要更新的作品不存在");
            }

            // 更新 Work 的属性
            existingWork.setWorkname(workDTO.getWorkname());
            existingWork.setStatus(workDTO.getStatus());
            existingWork.setDescription(workDTO.getDescription());
            existingWork.setCategory(workDTO.getCategory());
            existingWork.setTeachstack(workDTO.getTeachstack());
            existingWork.setDate(LocalDate.now());

            String competitionName = workDTO.getCompetitionName();
            if (competitionName == null || competitionName.isEmpty()) {
                System.out.println("未提供竞赛名称");
                throw new RuntimeException("未提供竞赛名称");
            }

            System.out.println("根据竞赛名称查询对应的竞赛信息");
            Competitions competition = competitionsModel.newQuery()
                    .where("name", competitionName)
                    .first()
                    .getEntity();
            if (competition != null) {
                existingWork.setCompetitionid(String.valueOf(competition.getId()));
            } else {
                System.out.println("未找到对应的竞赛信息");
                throw new RuntimeException("未找到对应的竞赛信息");
            }

            // 更新 Work 记录
            System.out.println("开始更新 Work 记录");
            workModel.newQuery().where("workId", workId).update(existingWork);
            System.out.println("成功更新 Work 记录，ID: " + workId);

            // 处理 WorkUser 的更新，这里简单假设先删除旧的再插入新的
            System.out.println("开始处理 WorkUser 记录");
            workUserModel.newQuery().where("workId", workId).delete();
            List<WorkUser> workUsers = workDTO.getWorkUsers();
            for (WorkUser workUser : workUsers) {
                // 生成唯一的 workUserId
                String workUserId = UUID.randomUUID().toString();
                workUser.setId(workUserId);
                workUser.setWorkid(workId);

                try {
                    // 插入 WorkUser 记录
                    System.out.println("开始插入 WorkUser 记录");
                    workUserModel.newQuery().insert(workUser);
                    System.out.println("成功插入 WorkUser 记录，ID: " + workUser.getId());
                } catch (SQLRuntimeException e) {
                    System.out.println("插入 WorkUser 记录时的 SQL 错误: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("插入 WorkUser 记录失败: " + e.getMessage());
                }
            }

            return existingWork;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("更新数据时发生异常：" + e.getMessage());
            return null;
        }
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
        }

        return works;
    }

}