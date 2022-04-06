package com.chessplusplus;

public interface FireBaseInterface {
    void getStatus();
    void sendInitialState (String id, String state);
    void sendMove (String id, String move);
    void getGameUpdates (String id);
    String createGameID ();
    void loginAnonymously();
    boolean isConnected();
    boolean joinGame(String gameID);
    void goOnline();
}
