package com.chessplusplus.game.component;

import com.chessplusplus.model.piece.Piece;
import com.chessplusplus.model.piece.PieceType;
import com.chessplusplus.model.ChessTurn;
import com.chessplusplus.model.board.Position;
import com.google.gson.Gson;

import org.junit.Test;

import java.util.ArrayList;


public class TurnToJsonTest {
    @Test
    public void basicTest() {
        Piece p = new Piece("123", PieceType.BISHOP, Position.pos(1,1), null);
        ChessTurn.Action a = new ChessTurn.Action(p, ChessTurn.ActionType.CREATION, Position.pos(1,1), Position.pos(1,1));
        ArrayList l = new ArrayList();
        l.add(a);
        ChessTurn t = new ChessTurn("123", l);
        String ts = new Gson().toJson(t);
        ChessTurn t2 = new Gson().fromJson(ts, t.getClass());
        assert t.toString().equals(t2.toString());
    }
}
