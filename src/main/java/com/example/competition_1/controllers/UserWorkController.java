package com.example.competition_1.controllers;

import com.example.competition_1.models.entity.Work;
import com.example.competition_1.services.UserWorkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/work")
public class UserWorkController {

    private final UserWorkService userWorkService;

    public UserWorkController(UserWorkService userWorkService) {
        this.userWorkService = userWorkService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Work>> getWorksByUserId(@PathVariable("userId") String userId) {
        try {
            List<Work> works = userWorkService.getWorksByUserId(userId);
            return ResponseEntity.ok(works);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}