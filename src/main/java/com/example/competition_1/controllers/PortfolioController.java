package com.example.competition_1.controllers;

import com.example.competition_1.models.entity.Work;
import com.example.competition_1.models.entity.WorkUser;
import com.example.competition_1.services.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/work")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("/add")
    public ResponseEntity<Work> addPortfolio(@RequestBody Work work, @RequestParam("teamMembers") List<WorkUser> workUsers) {
        if (work == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Work addedWork = portfolioService.addPortfolio(work, workUsers);
            if (addedWork != null) {
                return ResponseEntity.ok(addedWork);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Work());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Work());
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePortfolio(@RequestParam("workId") String workId) {
        boolean isDeleted = portfolioService.deletePortfolio(workId);
        if (isDeleted) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("删除失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<Work> updatePortfolio(@RequestParam("workId") String workId,
                                                @RequestBody Work updatedWork,
                                                @RequestParam(value = "teamMembers", required = false) List<WorkUser> workUsers) {

        if (updatedWork == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Work result = portfolioService.updatePortfolio(workId, updatedWork, workUsers);
            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Work());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Work());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Work>> getAllPortfolios() {
        try {
            List<Work> portfolios = portfolioService.getAllPortfolios();
            return ResponseEntity.ok(portfolios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}