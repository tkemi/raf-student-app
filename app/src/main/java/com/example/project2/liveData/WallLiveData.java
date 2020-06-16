package com.example.project2.liveData;

import com.example.project2.model.WallMessage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class WallLiveData extends LiveData<List<WallMessage>> {

    private ValueEventListener valueEventListener;
    private DatabaseReference databaseReference;

    public WallLiveData(){
        observers();
    }

    private void observers(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = firebaseDatabase.getReference().child("wall");

        this.valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<WallMessage> data = new ArrayList<>();

                if(dataSnapshot.getValue() == null){
                    return;
                }

                WallMessage wallMessage = null;
                for(DataSnapshot dss:dataSnapshot.getChildren()){
                    wallMessage = dss.getValue(WallMessage.class);

                    data.add(wallMessage);
                }

                addData(data);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

    }



    private void addData(List<WallMessage> data){
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
