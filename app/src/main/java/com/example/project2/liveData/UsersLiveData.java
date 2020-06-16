package com.example.project2.liveData;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.project2.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class UsersLiveData extends LiveData<List<User>> {

    private static final  String USERNAME_KEY ="userNameKey";
    private static final String INDEX_ID_KEY ="indexIdKey";

    private ValueEventListener valueEventListener;
    private DatabaseReference databaseReference;
    private Application application;

    public UsersLiveData(Application application){
        super();
        this.application = application;
        init();
    }

    private void init(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = firebaseDatabase.getReference().child("users/");
        this.valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> data = new ArrayList<>();

                SharedPreferences sharedPreferences = application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);

                String username = sharedPreferences.getString(USERNAME_KEY,null);
                String index = sharedPreferences.getString(INDEX_ID_KEY,null);


                if(dataSnapshot.getValue() == null){
                    return;
                }

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    String id = ds.getKey();
                    user.setIndexId(id);
                    if( ! ( username.equals(user.getName()) && index.equals(user.getIndexId()) ) ){
                        data.add(user);
                    }
                }

                addData(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

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

    private void addData(List<User> data){
        setValue(data);
    }

}
