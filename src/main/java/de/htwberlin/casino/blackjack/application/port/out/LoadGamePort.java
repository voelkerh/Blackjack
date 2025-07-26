package de.htwberlin.casino.blackjack.application.port.out;

import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;

public interface LoadGamePort {
    GameImpl retrieveGame(Long gameId);
}
