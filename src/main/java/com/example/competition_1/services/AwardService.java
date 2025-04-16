package com.example.competition_1.services;

import com.example.competition_1.models.entity.Award;
import org.springframework.stereotype.Service;
import gaarason.database.contract.eloquent.RecordList;
import gaarason.database.contract.eloquent.Record;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AwardService {

    private final Award.Model awardModel;

    public AwardService(Award.Model awardModel) {
        this.awardModel = awardModel;
    }

    public boolean addAward(Award award) {
        award.setCreateat(LocalDateTime.now());
        String insertedId = awardModel.newQuery().insertGetId(award);
        return insertedId != null;
    }

    public boolean updateAward(String awarId, Award updatedAward) {
        updatedAward.setAwarid(awarId);
        int updatedCount = awardModel.newQuery()
                .where("awarId", awarId)
                .update(updatedAward);
        return updatedCount > 0;
    }

    // 删除获奖信息
    public boolean deleteAward(String awarId) {
        int deletedCount = awardModel.newQuery()
                .where("awarId", awarId)
                .delete();
        return deletedCount > 0;
    }

    // 查看所有获奖记录
    public List<Award> getAllAwards() {
        RecordList<Award, ?> records = awardModel.newQuery().get();
        List<Award> awards = records.stream()
                .map(Record::getEntity)
                .collect(Collectors.toList());

        return awards;
    }
}