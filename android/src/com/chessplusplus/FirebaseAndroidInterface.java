package com.chessplusplus;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseAndroidInterface implements FireBaseInterface{
    FirebaseDatabase database;
    DatabaseReference myRef;

    public FirebaseAndroidInterface(){
        database = FirebaseDatabase.getInstance("https://chessplusplus-815c2-default-rtdb.europe-west1.firebasedatabase.app");
        myRef = database.getReference("message");
    }


    @Override
    public void Status() {
        System.out.println("################## Starting in Android, Firebase accessible.");
    }

    @Override
    public void FirstFireBaseTest() {
        if (myRef != null)
            myRef.setValue("Hello World!");
        else
            System.out.println("Something went wrong with Firebase");
    }

    @Override
    public void SetOnValueChangedListener() {

    }

    @Override
    public void SetValueInDb(String target, String value) {

    }
}
