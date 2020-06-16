package com.example.project2.viewmodel;

import android.app.Application;

import com.example.project2.model.ScheduleItem;
import com.example.project2.model.ScheduleItemFilter;
import com.example.project2.repository.ScheduleRepository;
import com.example.project2.repository.db.entity.ScheduleItemEntity;
import com.example.project2.repository.web.model.Resource;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class ScheduleViewModel extends AndroidViewModel {
    private ScheduleRepository scheduleRepository;

    private MutableLiveData<ScheduleItemFilter> mFilterLiveData;
    private LiveData<List<ScheduleItemEntity>> mFilteredLiveData;

    public ScheduleViewModel(@NonNull Application application) {
        super(application);
        scheduleRepository = new ScheduleRepository(application);
        mFilterLiveData = new MutableLiveData<>();

        mFilteredLiveData = Transformations.switchMap(mFilterLiveData, new Function<ScheduleItemFilter, LiveData<List<ScheduleItemEntity>>>() {
            @Override
            public LiveData<List<ScheduleItemEntity>> apply(ScheduleItemFilter filter) {
                return scheduleRepository.getFilteredMovies(filter);
            }
        });
    }

    public LiveData<Resource<Void>> getSheduleItems() {
        return scheduleRepository.getScheduleItems();
    }

    public LiveData<List<ScheduleItemEntity>> getFilteredScheduleItems(){
        return mFilteredLiveData;

    }

    public LiveData<List<ScheduleItemEntity>> getAllScheduleEntries() {
        return scheduleRepository.getAllScheduleItems();
    }

    public void setFilter(ScheduleItemFilter scheduleFilter){
        mFilterLiveData.setValue(scheduleFilter);
    }

}
