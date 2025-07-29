package de.htwberlin.casino.blackjack.application.domain.model.game;

/**
 * GameState used to identify whether a game is ongoing or has a specific result already.
 */
public enum GameState {
    PLAYING, WON, LOST, PUSH, BLACKJACK
}
