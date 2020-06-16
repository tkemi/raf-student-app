package com.example.project2.repository.web.service;

import com.example.project2.repository.web.model.ScheduleItemApiModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ScheduleService {

    @GET("raspored/json.php")
    public Call<List<ScheduleItemApiModel>> getSchedule();
}
