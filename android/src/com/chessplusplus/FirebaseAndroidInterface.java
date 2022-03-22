package com.chessplusplus;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class FirebaseAndroidInterface implements FireBaseInterface{
    FirebaseDatabase database;
    DatabaseReference myRef;

    // Singleton?
    public FirebaseAndroidInterface(){
        database = FirebaseDatabase.getInstance("https://chessplusplus-815c2-default-rtdb.europe-west1.firebasedatabase.app");
        myRef = database.getReference("games");
    }

    // ping-echo?
    @Override
    public void getStatus() {
        System.out.println("################## Starting in Android, Firebase accessible.");
    }

    @Override
    public void sendInitialState(String id, String state) {
        myRef.child(id).child("start").setValue(state);
    }

    @Override
    public void sendMove(String id, String move) {
        // we need to figure out where to get IDs for moves
        myRef.child(id).child(move.substring(0,1)).setValue(move);
    }

    @Override
    public void getGameUpdates(String id) {
        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                System.out.println("value has changed to: " + map);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value: " + error.toException());
            }
        });
    }
}
