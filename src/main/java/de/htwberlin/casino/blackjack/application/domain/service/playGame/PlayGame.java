package de.htwberlin.casino.blackjack.application.domain.service.playGame;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;

import java.util.List;

public interface PlayGame {

    boolean handHasInitialBlackjack(Hand hand);

    boolean isPlayerBusted(Game game);

    Card playPlayerTurn(Game game);

    List<Card> playDealerTurn(Game game);

    GameState determineResult(Hand playerHand, Hand dealerHand);
}
