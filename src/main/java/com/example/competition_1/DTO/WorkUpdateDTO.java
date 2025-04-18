package com.example.competition_1.DTO;

import com.example.competition_1.models.entity.Work;
import com.example.competition_1.models.entity.WorkUser;

import java.util.List;

public class WorkUpdateDTO {

    private Work work;
    private List<WorkUser> workUsers;

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public List<WorkUser> getWorkUsers() {
        return workUsers;
    }

    public void setWorkUsers(List<WorkUser> workUsers) {
        this.workUsers = workUsers;
    }
}