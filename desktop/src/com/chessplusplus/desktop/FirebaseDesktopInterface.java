package com.chessplusplus.desktop;

import com.chessplusplus.FireBaseInterface;

public class FirebaseDesktopInterface implements FireBaseInterface {
    @Override
    public void getStatus() {
        System.out.println("################## Starting in Desktop, Firebase not accessible.");
    }

    @Override
    public void sendInitialState(String id, String state) {

    }

    @Override
    public void sendMove(String id, String move) {

    }

    @Override
    public void getGameUpdates(String id) {

    }
}
