package com.example.competition_1.services;

import com.example.competition_1.models.entity.Award;
import org.springframework.stereotype.Service;
import gaarason.database.contract.eloquent.RecordList;
import gaarason.database.contract.eloquent.Record;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AwardService {

    private final Award.Model awardModel;

    public AwardService(Award.Model awardModel) {
        this.awardModel = awardModel;
    }


    public boolean addAward(Award award) {
        try {
            // 设置创建时间
            award.setCreateat(LocalDateTime.now());

            // 生成唯一的 awardId
            award.setAwarid(UUID.randomUUID().toString());

            // 插入数据
            awardModel.newQuery().insert(award);
            System.out.println("成功插入数据，ID: " + award.getAwarid());
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