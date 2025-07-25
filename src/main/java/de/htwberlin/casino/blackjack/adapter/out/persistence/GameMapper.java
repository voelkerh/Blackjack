package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public Game mapToDomainEntity(Long gameId, Long userId, CardDeck cardDeck,
                                  PlayerHand playerHand, DealerHand dealerHand, GameState gameState, double bet) {
        return new Game(gameId, userId, cardDeck,
                playerHand, dealerHand, gameState, bet);
    }

}
