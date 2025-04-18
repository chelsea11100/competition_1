package com.example.competition_1.DTO;

import com.example.competition_1.models.entity.WorkUser;
import java.time.LocalDate;
import java.util.List;

public class WorkDTO {
    private String workname;
    private Integer status;
    private String description;
    private String category;
    private String teachstack;
    private LocalDate date;
    private List<WorkUser> workUsers;
    private String competitionName;

    // Getters and Setters
    public String getWorkname() {
        return workname;
    }

    public void setWorkname(String workname) {
        this.workname = workname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTeachstack() {
        return teachstack;
    }

    public void setTeachstack(String teachstack) {
        this.teachstack = teachstack;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<WorkUser> getWorkUsers() {
        return workUsers;
    }

    public void setWorkUsers(List<WorkUser> workUsers) {
        this.workUsers = workUsers;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }
}