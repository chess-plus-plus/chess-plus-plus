package com.chessplusplus;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class FirebaseAndroidInterface implements FireBaseInterface{
    FirebaseDatabase database;
    DatabaseReference dataRef;
    DatabaseReference pingRef;
    AndroidFirebaseAuth mAuth;
    FirebaseUser user;

    boolean connected;
    boolean gameExists;

    // Singleton?
    public FirebaseAndroidInterface(){
        database = FirebaseDatabase.getInstance("https://chessplusplus-815c2-default-rtdb.europe-west1.firebasedatabase.app");
        dataRef = database.getReference("games");
        pingRef = database.getReference(".info/connected");
        mAuth = new AndroidFirebaseAuth();
        this.getStatus();
        this.loginAnonymously();
    }

    public FirebaseUser getCurrentUser() {
        if (this.user != null)
            return user;
        return mAuth.getUser();
    }

    public void loginAnonymously() {
        if (this.user != null)
            return;
        user = mAuth.loginAnonymously();
    }

    // ping-echo
    @Override
    public void getStatus() {
        pingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    System.out.println("###FIREBASE### connected!");
                } else {
                    System.out.println("###FIREBASE### not connected!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("###FIREBASE### connection listener was cancelled!");
            }
        });
    }

    @Override
    public void sendInitialState(String id, String state) {
        dataRef.child(id).child("start").setValue(state);
    }

    @Override
    public void sendMove(String id, String move) {
        // we need to figure out where to get IDs for moves
        dataRef.child(id).child(move.substring(0,1)).setValue(move);
    }

    @Override
    public void getGameUpdates(String id) {
        dataRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                System.out.println("###FIREBASE### value has changed to: " + map);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("###FIREBASE### Failed to read value: " + error.toException());
            }
        });
    }

    @Override
    public String createGameID() {
        user = this.getCurrentUser();
        if (user == null)
            return null;
        String key = dataRef.push().getKey();
        dataRef.child(key).child("player1").setValue(user.getUid());
        this.getGameUpdates(key);
        return key;
    }

    public boolean joinGame(String gameID) {
        user = this.getCurrentUser();
        if (user == null)
            return false;
        gameExists = false;
        dataRef.child(gameID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    gameExists = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (!gameExists)
            return false;
        dataRef.child(gameID).child("player2").setValue(user.getUid());
        this.getGameUpdates(gameID);
        return true;
    }

    public boolean isConnected() {
        return connected;
    }

    public void goOnline() {
        database.goOnline();
    }
}
