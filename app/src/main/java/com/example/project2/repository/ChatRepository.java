package com.example.project2.repository;

import android.app.Application;

import com.example.project2.liveData.UsersLiveData;


public class ChatRepository {

    private UsersLiveData usersLiveData;

    public ChatRepository(Application application){
        usersLiveData = new UsersLiveData(application);
    }

    public UsersLiveData getUsersLiveData() {
        return usersLiveData;
    }
}
