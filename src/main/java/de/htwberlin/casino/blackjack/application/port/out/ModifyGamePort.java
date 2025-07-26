package de.htwberlin.casino.blackjack.application.port.out;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;

public interface ModifyGamePort {
    Game saveGame(Game game);

    void saveCardDraw(Long gameId, Card card, String holder);

    void updateGameState(Long gameId, String state);
}
