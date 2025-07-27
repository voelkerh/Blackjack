package de.htwberlin.casino.blackjack.application.domain.model.game;

import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeck;
import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;

public interface Game {
    Long getId();

    String getUserId();

    GameState getGameState();

    CardDeck getCardDeck();

    Hand getPlayerHand();

    Hand getDealerHand();

    double getBet();

    boolean isPlayerBusted();

    boolean isDealerBusted();
}
