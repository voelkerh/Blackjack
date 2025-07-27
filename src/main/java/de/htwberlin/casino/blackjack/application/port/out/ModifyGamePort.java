package de.htwberlin.casino.blackjack.application.port.out;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
import org.springframework.transaction.annotation.Transactional;

public interface ModifyGamePort {
    @Transactional
    Game saveGame(Game game);

    @Transactional
    void saveCardDraw(Long gameId, Card card, HandType holder);

    @Transactional
    void updateGameState(Long gameId, String state);
}
