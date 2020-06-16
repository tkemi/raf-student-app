package com.example.project2.repository.web.model;

public class Resource<T> {

    private T mData;
    private boolean isSuccessful;

    public Resource(T mData, boolean isSuccessful) {
        this.mData = mData;
        this.isSuccessful = isSuccessful;
    }

    public T getmData() {
        return mData;
    }

    public void setmData(T mData) {
        this.mData = mData;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }
}
