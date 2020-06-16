package com.example.project2.repository.web.api;

import com.example.project2.repository.web.model.ScheduleItemApiModel;
import com.example.project2.repository.web.service.ScheduleService;
import com.example.project2.repository.web.service.ServiceGenerator;

import java.util.List;

import retrofit2.Call;

public class ScheduleApi {

    private ScheduleService scheduleService;

    public ScheduleApi() {
        scheduleService = ServiceGenerator.createService(ScheduleService.class);
    }

    public Call<List<ScheduleItemApiModel>> getSchedule(){
        return scheduleService.getSchedule();
    }
}
