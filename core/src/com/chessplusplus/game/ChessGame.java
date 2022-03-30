package com.chessplusplus.game;

import java.util.List;

/**
 * The Chess Game interface is designed to act as an intermediary between the
 * game itself (constructed and run using the Ashley ECS-system) and the rest
 * of the application.
 *
 * It translates to and from the ECS system to make it easy to interact with the
 * game without having to deal with the increases of the ECS-system.
 */
public interface ChessGame {

    List<Piece> getAllPieces();

    List<Piece> getPieces(int playerId);
}
