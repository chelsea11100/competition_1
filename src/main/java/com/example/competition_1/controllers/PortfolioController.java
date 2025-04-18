package com.example.competition_1.controllers;

import com.example.competition_1.DTO.WorkDTO;
import com.example.competition_1.DTO.WorkUpdateDTO;
import com.example.competition_1.models.entity.Work;
import com.example.competition_1.models.entity.WorkUser;
import com.example.competition_1.services.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/work")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("/add")
    public ResponseEntity<Work> addPortfolio(@RequestBody WorkDTO workDTO) {
        try {
            Work addedWork = portfolioService.addPortfolio(workDTO);
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
    @PutMapping("/update/{workId}")
    public ResponseEntity<Work> updatePortfolio(@PathVariable String workId, @RequestBody WorkDTO workDTO) {
        Work work = portfolioService.updatePortfolio(workId, workDTO);
        if (work != null) {
            return ResponseEntity.ok(work);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllPortfolios() {
        try {
            Map<String, Object> portfolios = portfolioService.getAllPortfolios();
            return ResponseEntity.ok(portfolios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}