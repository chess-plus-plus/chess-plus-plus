package com.chessplusplus.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.chessplusplus.game.component.PositionComponent;
import com.chessplusplus.game.system.MovementSystem;

// Ad hoc class used purely to test out ECS
// To "run" the demo, just click a button or on the application window,
// and you should see the position of the piece update each time.
public class Game {

    Engine engine = new Engine();
    Entity piece;

    public Game() {
        piece = new Entity();
        engine.addEntity(piece);

        piece.add(new PositionComponent(3, 0));
        //piece.add(new MovementComponent(0, 1));
        MovementSystem movementSystem = new MovementSystem();
        engine.addSystem(movementSystem);

        System.out.println(piece.getComponent(PositionComponent.class));

    }

    public void update() {
        System.out.println(piece.getComponent(PositionComponent.class));
        engine.update(1);
    }

}
