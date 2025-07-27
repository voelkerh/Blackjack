package de.htwberlin.casino.blackjack.application.domain.service.playGame;

import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;

public interface PlayGame {

    boolean checkInitialBlackJack(Game game);

    boolean isPlayerBusted(Game game);

    void playDealerTurn(Game game);

    GameState determineResult(Game game);
}
