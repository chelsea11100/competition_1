package com.example.competition_1.services;

import com.example.competition_1.models.entity.Work;
import com.example.competition_1.models.entity.WorkUser;
import gaarason.database.contract.eloquent.Model;
import gaarason.database.contract.eloquent.Record;
import gaarason.database.contract.eloquent.RecordList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserWorkService {

    private final Work.Model workModel;
    private final WorkUser.Model workUserModel;

    public UserWorkService(Work.Model workModel, WorkUser.Model workUserModel) {
        this.workModel = workModel;
        this.workUserModel = workUserModel;
    }

    public List<Work> getWorksByUserId(String userId) {
        // 根据 userId 在 work_user 表中查找对应的 workId
        RecordList<WorkUser, ?> workUserRecords = workUserModel.newQuery()
                .where("userId", userId)
                .get();
        List<String> workIds = workUserRecords.stream()
                .map(Record::getEntity)
                .map(WorkUser::getWorkid)
                .collect(Collectors.toList());

        // 如果没有找到对应的 workId，返回空列表
        if (workIds.isEmpty()) {
            return new ArrayList<>();
        }

        // 根据 workId 在 work 表中查询所有相同 workId 的信息
        RecordList<Work, ?> workRecords = workModel.newQuery()
                .whereIn("workId", workIds)
                .get();
        return workRecords.stream()
                .map(Record::getEntity)
                .collect(Collectors.toList());
    }
}