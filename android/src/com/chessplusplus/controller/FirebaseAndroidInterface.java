package com.chessplusplus.controller;

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

public class FirebaseAndroidInterface implements FireBaseInterface {

    private static FirebaseAndroidInterface instance;
    private FirebaseDatabase database;
    private DatabaseReference dataRef;
    private DatabaseReference pingRef;
    private DatabaseReference IDRef;
    private AndroidFirebaseAuth mAuth;
    private FirebaseUser user;
    private DataSnapshot gameIDs;
    private DataSnapshot currentGame;

    private String forfeitPlayerID;

    private boolean connected;
    private boolean gameExists;
    private boolean hasUpdates;
    private boolean allPlayersConnected;

    public static FirebaseAndroidInterface getInstance() {
        if (instance == null) {
            instance = new FirebaseAndroidInterface();
        }
        return instance;
    }

    private FirebaseAndroidInterface(){
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
        long moveCount = this.currentGame.getChildrenCount() - 2;

        dataRef.child(id).child(String.valueOf(moveCount + 1)).setValue(move);
    }

    public void sendForfeit(String gameID, String playerID) {
        dataRef.child(gameID).child("forfeit").setValue(playerID);
    }

    public String getForfeitPlayerID() {
        return this.forfeitPlayerID;
    }

    @Override
    public void getGameUpdates(String id) {
        allPlayersConnected = false;
        dataRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                currentGame = dataSnapshot;
                if (dataSnapshot.child("player1").exists() && dataSnapshot.child("player2").exists())
                    allPlayersConnected = true;
                if (dataSnapshot.child("forfeit").exists())
                    forfeitPlayerID = dataSnapshot.child("forfeit").getValue().toString();
                else
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
        if (user == null || this.connected == false)
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
                    System.out.println("something went wrong: " + error);
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

    public boolean allPlayersConnected() {
        return this.allPlayersConnected;
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


