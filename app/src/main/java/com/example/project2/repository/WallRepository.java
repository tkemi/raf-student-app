package com.example.project2.repository;

import com.example.project2.liveData.WallLiveData;
import com.example.project2.model.WallMessage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WallRepository {

    private WallLiveData wallLiveData;
    private DatabaseReference databaseReference;

    public WallRepository(){
        this.wallLiveData = new WallLiveData();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = firebaseDatabase.getReference().child("wall");
    }

    public WallLiveData getWallLiveData() {
        return wallLiveData;
    }

    public void addWallMessage(WallMessage wallMessage){
        databaseReference.push().setValue(wallMessage); // ovde nisam nesto siguran verovatno je dobro sve
    }

}
