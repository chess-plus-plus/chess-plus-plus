package com.chessplusplus;

import com.chessplusplus.game.Turn;
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

    public void sendTurn(String gameID, Turn turn) {
        this.FBIC.sendMove(gameID, gson.toJson(turn));
    }

    public Turn getNewTurnIfAvailable () {
        if (!this.FBIC.hasUpdates())
            return null;
        String lm = this.FBIC.getLatestMove();
        if (lm == null)
            return null;
        return gson.fromJson(lm, Turn.class);
    }

    public void sendForfeit(String gameID, String playerID) {
        this.FBIC.sendForfeit(gameID, playerID);
    }

    public String getForfeitPlayerID() {
        return this.FBIC.getForfeitPlayerID();
    }
}
