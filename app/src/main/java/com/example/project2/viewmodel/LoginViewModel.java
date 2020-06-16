package com.example.project2.viewmodel;

import android.app.Application;

import com.example.project2.model.User;
import com.example.project2.model.UserResponse;
import com.example.project2.repository.AuthRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LoginViewModel extends AndroidViewModel {

    private AuthRepository mAuthRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mAuthRepository = new AuthRepository(application);
    }

    public void logInUser(String indexId,String username){
        User user = new User(indexId,username);
        mAuthRepository.storeUser(user);
    }

    public LiveData<UserResponse> getUserStoreLiveData(){
        return mAuthRepository.getUserStoreLiveData();
    }
}
