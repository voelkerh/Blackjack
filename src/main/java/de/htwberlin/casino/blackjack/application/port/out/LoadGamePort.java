package de.htwberlin.casino.blackjack.application.port.out;

import de.htwberlin.casino.blackjack.application.domain.model.Game;

public interface LoadGamePort {
    Game retrieveGame(int gameId);
}
