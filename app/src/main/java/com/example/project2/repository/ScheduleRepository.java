package com.example.project2.repository;

import android.app.Application;
import android.util.Log;

import com.example.project2.model.ScheduleItemFilter;
import com.example.project2.repository.db.ScheduleDatabase;
import com.example.project2.repository.db.dao.ScheduleDao;
import com.example.project2.repository.db.entity.ScheduleItemEntity;
import com.example.project2.repository.web.api.ScheduleApi;
import com.example.project2.repository.web.model.Resource;
import com.example.project2.repository.web.model.ScheduleItemApiModel;
import com.example.project2.util.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleRepository {

    private static final String TAG = "ScheduleRepository";


    private ExecutorService executorService;
    private ScheduleApi scheduleApi;
    private ScheduleDao scheduleDao;

    private MutableLiveData<Resource<Void>> mResourceLiveData;

    public ScheduleRepository(Application application){
        scheduleApi = new ScheduleApi();
        scheduleDao = ScheduleDatabase.getdb(application).getScheduleDao();
        mResourceLiveData = new MutableLiveData<>();
        executorService = Executors.newCachedThreadPool();
    }

    public LiveData<Resource<Void>> getScheduleItems(){
        scheduleApi.getSchedule().enqueue(new Callback<List<ScheduleItemApiModel>>() {
            @Override
            public void onResponse(Call<List<ScheduleItemApiModel>> call, Response<List<ScheduleItemApiModel>> response) {
                Resource<Void> resource = new Resource<>(null,true);
                mResourceLiveData.setValue(resource);
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        scheduleDao.deleteAll();
                    }
                });
                insertScheduleItemsToDatabase(response.body());
            }

            @Override
            public void onFailure(Call<List<ScheduleItemApiModel>> call, Throwable t) {
                Resource<Void> resource = new Resource<>(null,false);
                Log.e(TAG,"onFailure: "+t.toString());
            }
        });
        return mResourceLiveData;
    }

    public LiveData<List<ScheduleItemEntity>> getFilteredMovies(ScheduleItemFilter scheduleItemFilter){

        String classNameOrProfessor = "%"+scheduleItemFilter.getClassNameOrProfessor().toUpperCase()+"%";
        String day = "%"+scheduleItemFilter.getDay().toUpperCase()+"%";
        String group = "%"+scheduleItemFilter.getGroup().toUpperCase()+"%";
        Log.e("ScheduleItemFilter: ","Name: " + classNameOrProfessor+ " Day: " + day + " Group: "+group);
        return scheduleDao.getFilteredScheduleItems(classNameOrProfessor,day,group);
    }


    private void insertScheduleItemsToDatabase(List<ScheduleItemApiModel> scheduleItemApiModels){
        List<ScheduleItemEntity> scheduleItemEntities = transformApiModelToEntity(scheduleItemApiModels);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                scheduleDao.insertScheduleItems(scheduleItemEntities);
            }
        });
    }

    private List<ScheduleItemEntity> transformApiModelToEntity(List<ScheduleItemApiModel> scheduleItemApiModels){
        List<ScheduleItemEntity> scheduleItemEntities = new ArrayList<>();

        for(ScheduleItemApiModel item:scheduleItemApiModels){
            String className = item.getClassName();
            String classType = item.getClassType();
            String professor = item.getProfessor();
            String groups = item.getGroups();
            String day = item.getDay();
            String time = item.getTime();
            String classroom = item.getClassroom();
            String uid = Random.getRandomUID();

            ScheduleItemEntity scheduleItemEntity = new ScheduleItemEntity(uid,className,classType,professor,groups,day,time,classroom);
            scheduleItemEntities.add(scheduleItemEntity);
        }
        return  scheduleItemEntities;
    }

    public LiveData<List<ScheduleItemEntity>> getAllScheduleItems(){
            return scheduleDao.getAllScheduleItems();
    }

    public LiveData<ScheduleItemEntity> getSchedulelItemById(String id){
        return scheduleDao.getScheduleItemById(id);
    }

}
