package de.htwberlin.casino.blackjack.application.port.out;

import de.htwberlin.casino.blackjack.adapter.out.persistence.GameJpaEntity;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;

public interface ModifyGamePort {
    Game saveGame(Game game);

    void saveCardDraw(GameJpaEntity game, Card card, String holder);

    void updateGameState(Long gameId, String state);
}
