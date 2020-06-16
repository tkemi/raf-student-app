package com.example.project2.viewmodel;

import android.app.Application;

import com.example.project2.model.User;
import com.example.project2.repository.ChatRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ChatViewModel extends AndroidViewModel {

    private ChatRepository chatRepository;
    private LiveData<List<User>> usersLiveData;


    public ChatViewModel(@NonNull Application application) {
        super(application);
        this.chatRepository = new ChatRepository(getApplication());
        usersLiveData = this.chatRepository.getUsersLiveData();
    }

    public LiveData<List<User>> getUsersLiveData() {
        return usersLiveData;
    }
}
