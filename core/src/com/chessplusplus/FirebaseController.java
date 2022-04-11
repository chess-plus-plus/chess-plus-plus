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

    public String createNewGame() {
        String gameID = this.FBIC.createGameID();

        System.out.println("created game with gameID: " + gameID);

        return gameID;

        // do smth in front end
    }

    public void joinGame(String gameID) {
        // if joined is true, the ID exists and everything worked
        // otherwise, some kind of error occured
        boolean joined = this.FBIC.joinGame(gameID);
        if (joined)
            System.out.println("joined game " + gameID);
    }

    public void reconnect () {
        this.FBIC.goOnline();
    }
}
