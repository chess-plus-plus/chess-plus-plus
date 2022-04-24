package com.chessplusplus;

import com.chessplusplus.game.ChessTurn;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FirebaseController {
    FireBaseInterface FBIC;
    Gson gson;

    public FirebaseController (FireBaseInterface FBIC) {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
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

    public boolean joinGame(String gameID) {
        // if joined is true, the ID exists and everything worked
        // otherwise, some kind of error occured
        boolean joined = this.FBIC.joinGame(gameID);
        if (joined)
            System.out.println("joined game " + gameID);
        return(joined);
    }

    public void reconnect () {
        this.FBIC.goOnline();
    }

    public void sendTurn(String gameID, ChessTurn turn) {
        this.FBIC.sendMove(gameID, gson.toJson(turn));
    }

    public ChessTurn getNewTurnIfAvailable () {
        if (!this.FBIC.hasUpdates())
            return null;
        String lm = this.FBIC.getLatestMove();
        if (lm == null)
            return null;
        return gson.fromJson(lm, ChessTurn.class);
    }

    public boolean allPlayersAreConnected() {
        return this.FBIC.allPlayersConnected();
    }
}
