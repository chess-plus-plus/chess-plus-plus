package com.chessplusplus;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FirebaseAndroidInterface implements FireBaseInterface{
    FirebaseDatabase database;
    DatabaseReference dataRef;
    DatabaseReference pingRef;
    DatabaseReference IDRef;
    AndroidFirebaseAuth mAuth;
    FirebaseUser user;
    DataSnapshot gameIDs;
    DataSnapshot currentGame;

    boolean connected;
    boolean gameExists;
    boolean hasUpdates;

    // Singleton?
    public FirebaseAndroidInterface(){
        database = FirebaseDatabase.getInstance("https://chessplusplus-815c2-default-rtdb.europe-west1.firebasedatabase.app");
        dataRef = database.getReference("games");
        IDRef = database.getReference("ids");
        pingRef = database.getReference(".info/connected");
        mAuth = new AndroidFirebaseAuth();
        getAllGameIDs();
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

    public void getAllGameIDs() {
        IDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gameIDs = snapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });
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
                currentGame = dataSnapshot;
                hasUpdates = true;
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

        String key = this.getRandomNumberString();

        IDRef.child(key).setValue(new Date());
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
                } else {
                    gameExists = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("smth wrong: " + error);
                    gameExists = false;
            }
        });
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!gameExists)
            return false;

        dataRef.child(gameID).child("player2").setValue(user.getUid());
        this.getGameUpdates(gameID);
        return true;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean hasUpdates(){
        if (hasUpdates) {
            hasUpdates = false;
            return true;
        }
        return false;
    }

    public String getLatestMove() {
        if (this.currentGame == null)
            return null;
        long moveCount = this.currentGame.getChildrenCount() - 2;
        Map<String, Object> map = (Map<String, Object>) this.currentGame.getValue();
        if (map.get(String.valueOf(moveCount)) != null)
            return String.valueOf(map.get(String.valueOf(moveCount)));
        return null;
    }

    public void goOnline() {
        database.goOnline();
    }

    // gameID generator
    private String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // can lead to infinite loop if db is full
        if (gameIDs.hasChild(String.valueOf(number)))
            return getRandomNumberString();

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}


