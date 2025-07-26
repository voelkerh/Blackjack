package de.htwberlin.casino.blackjack.application.port.out;

import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;
import jakarta.persistence.EntityNotFoundException;

public interface LoadGamePort {
    /**
     * Loads a game by its ID from the database and maps it to the domain model.
     *
     * @param gameId the ID of the game
     * @return the mapped domain {@link GameImpl} object
     * @throws EntityNotFoundException if the game is not found
     */
    GameImpl retrieveGame(Long gameId);
}
