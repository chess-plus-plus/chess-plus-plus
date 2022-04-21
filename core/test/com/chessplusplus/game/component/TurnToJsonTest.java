package com.chessplusplus.game.component;

import com.chessplusplus.game.Piece;
import com.chessplusplus.game.PieceType;
import com.chessplusplus.game.Turn;
import com.google.gson.Gson;

import org.junit.Test;

import java.util.ArrayList;


public class TurnToJsonTest {
    @Test
    public void basicTest() {
        Piece p = new Piece("123", PieceType.BISHOP, Position.pos(1,1), null);
        Turn.Action a = new Turn.Action(p, Turn.ActionType.CREATION, Position.pos(1,1), Position.pos(1,1));
        ArrayList l = new ArrayList();
        l.add(a);
        Turn t = new Turn("123", l);
        String ts = new Gson().toJson(t);
        Turn t2 = new Gson().fromJson(ts, t.getClass());
        assert t.toString().equals(t2.toString());
    }
}
