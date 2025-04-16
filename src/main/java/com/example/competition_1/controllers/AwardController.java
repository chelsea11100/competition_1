package com.example.competition_1.controllers;

import com.example.competition_1.models.entity.Award;
import com.example.competition_1.services.AwardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/price")
public class AwardController {

    private final AwardService awardService;

    public AwardController(AwardService awardService) {
        this.awardService = awardService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAward(@RequestBody Award award) {
        boolean isAdded = awardService.addAward(award);
        if (isAdded) {
            return new ResponseEntity<>("添加成功", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("添加失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 修改获奖信息
    @PutMapping("/update")
    public ResponseEntity<String> updateAward(@RequestParam("awarId") String awarId, @RequestBody Award updatedAward) {
        boolean isUpdated = awardService.updateAward(awarId, updatedAward);
        if (isUpdated) {
            return new ResponseEntity<>("修改成功", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("修改失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 删除获奖信息
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAward(@RequestParam("awarId") String awarId) {
        boolean isDeleted = awardService.deleteAward(awarId);
        if (isDeleted) {
            return new ResponseEntity<>("删除成功", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("删除失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 查看所有获奖记录
    @GetMapping("/all")
    public ResponseEntity<List<Award>> getAllAwards() {
        List<Award> awards = awardService.getAllAwards();
        return new ResponseEntity<>(awards, HttpStatus.OK);
    }

}
