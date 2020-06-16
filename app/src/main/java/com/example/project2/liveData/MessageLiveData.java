package com.example.project2.liveData;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.project2.model.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class MessageLiveData extends LiveData<List<Message>> {

    private static final  String USERNAME_KEY ="userNameKey";
    private static final String INDEX_ID_KEY ="indexIdKey";


    private ValueEventListener valueEventListener;
    private DatabaseReference databaseReference;
    private Application application;
    private String id;

    public MessageLiveData(Application application,String id) {
        this.application = application;
        this.id = id;
        init();
    }

    private void init(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = firebaseDatabase.getReference().child("chats/" + id);
        this.valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Message> data = new ArrayList<>();

                SharedPreferences sharedPreferences = application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);

                String username = sharedPreferences.getString(USERNAME_KEY,null);
                String index = sharedPreferences.getString(INDEX_ID_KEY,null);


                if(dataSnapshot.getValue() == null){
                    return;
                }

                for(DataSnapshot ds:dataSnapshot.getChildren()){ // ovde cu valjda da priveravam dal je 1 ili 0 za tip!!!
                    Log.e("DAtaSnapshot",ds.toString());

                    Message message = ds.getValue(Message.class);


                    if(message.getSenderIndex() != null) {
                        Log.e("DSAD",message.getSenderIndex());
                    }

                    if(message.getSenderIndex().equals(index)){
                        message.setType(1);
                    }else {
                        message.setType(0);
                    }

                    data.add(message);
                }

               addData(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }


    private void addData(List<Message> data){
        setValue(data);
    }

    @Override
    protected void onActive() {
        super.onActive();
        this.databaseReference.addValueEventListener(this.valueEventListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        this.databaseReference.removeEventListener(this.valueEventListener);
    }


}
