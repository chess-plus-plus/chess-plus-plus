package com.chessplusplus.desktop;

import com.chessplusplus.FireBaseInterface;

public class FirebaseDesktopInterface implements FireBaseInterface {
    @Override
    public void Status() {
        System.out.println("################## Starting in Desktop, Firebase not accessible.");
    }

    @Override
    public void FirstFireBaseTest() {

    }

    @Override
    public void SetOnValueChangedListener() {

    }

    @Override
    public void SetValueInDb(String target, String value) {

    }
}
