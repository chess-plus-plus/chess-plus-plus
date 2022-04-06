package com.chessplusplus;

public class FirebaseController {
    FireBaseInterface FBIC;

    public FirebaseController (FireBaseInterface FBIC) {
        this.FBIC = FBIC;
    }

    public boolean pingEcho() {
        return this.FBIC.isConnected();

        // do smth in front end
    }

    public void createNewGame() {
        // not fully implemented yet, will probably return null
        String gameID = this.FBIC.createGameID();

        // do smth in front end
    }
}
