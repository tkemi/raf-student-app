package com.example.project2.repository;

import android.app.Application;

import com.example.project2.liveData.MessageLiveData;
import com.example.project2.model.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageRepository {

    private MessageLiveData messageLiveData;
    private DatabaseReference databaseReference;

    public MessageRepository(Application application,String id){
        this.messageLiveData = new MessageLiveData(application,id);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = firebaseDatabase.getReference("chats/");
    }

    public MessageLiveData getMessageLiveData() {
        return messageLiveData;
    }

    public void addMessage(String id, Message message){
        databaseReference.child(id).push().setValue(message);
    }

}
