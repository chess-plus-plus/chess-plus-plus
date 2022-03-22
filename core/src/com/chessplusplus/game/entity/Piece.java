package com.chessplusplus.game.entity;


import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.chessplusplus.game.component.Position;
import com.chessplusplus.game.component.PositionComponent;

import java.util.List;

//TODO: Will likely be remodeled into a factory-esque class for easily creating piece entities.
public class Piece {

    public Entity entity = new Entity();
    public List<Component> movementComponents;
    public ComponentMapper<PositionComponent> positionMapper;

    public Piece(int startX, int startY) {
        entity.add(new PositionComponent(startX, startY));
        positionMapper = ComponentMapper.getFor(PositionComponent.class);
    }

    public Position getPosition() {
         return positionMapper.get(entity).position;
    }

}
