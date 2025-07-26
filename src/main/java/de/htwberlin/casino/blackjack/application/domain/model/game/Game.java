package de.htwberlin.casino.blackjack.application.domain.model.game;

import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;

public interface Game {
    Long getGameId();

    GameState getGameState();

    Hand getPlayerHand();

    Hand getDealerHand();

    double getBet();
}
