package com.example.project2.viewmodel;

import android.app.Application;
import android.net.Uri;

import com.example.project2.model.UserResponse;
import com.example.project2.repository.AuthRepository;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    private AuthRepository mAuthRepository;


    public MainViewModel(@NonNull Application application) {
        super(application);
        mAuthRepository = new AuthRepository(application);

    }

    public void logOut() {
        mAuthRepository.clearUser();
    }

    public LiveData<UserResponse> getUserStoreLiveData() {
        return mAuthRepository.getUserStoreLiveData();
    }
    
}
