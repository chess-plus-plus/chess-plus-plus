package com.chessplusplus.game.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.chessplusplus.game.component.MovementComponent;
import com.chessplusplus.game.component.PositionComponent;

public class MovementSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntities();
    }

    @Override
    public void update(float deltaTime) {
       for (Entity entity : entities) {
           MovementComponent movement = entity.getComponent(MovementComponent.class);
           PositionComponent position = entity.getComponent(PositionComponent.class);

           position.x += movement.deltaX;
           position.y += movement.deltaY;
       }
    }

}
