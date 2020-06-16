package com.example.project2.model;

public class UserResponse {

    private User mUser;

    private boolean isSuccessful;

    public UserResponse(User user, boolean isSuccessful) {
        mUser = user;
        this.isSuccessful = isSuccessful;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }
}
