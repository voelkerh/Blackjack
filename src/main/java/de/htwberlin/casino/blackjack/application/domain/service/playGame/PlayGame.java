package de.htwberlin.casino.blackjack.application.domain.service.playGame;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;

import java.util.List;

public interface PlayGame {

    boolean playerHasInitialBlackjack(Game game);

    boolean isPlayerBusted(Game game);

    Card playPlayerTurn(Game game);

    List<Card> playDealerTurn(Game game);

    GameState determineResult(Game game);
}
