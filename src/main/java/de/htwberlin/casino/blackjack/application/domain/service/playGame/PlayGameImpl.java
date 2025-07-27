package de.htwberlin.casino.blackjack.application.domain.service.playGame;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.CardDeck;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayGameImpl implements PlayGame {
    @Override
    public boolean playerHasInitialBlackjack(Game game) {
        Hand playerHand = game.getPlayerHand();
        return playerHand.getTotal() == 21;
    }

    @Override
    public boolean isPlayerBusted(Game game) {
        Hand playerHand = game.getPlayerHand();
        return playerHand.getTotal() > 21;
    }

    @Override
    public Card playPlayerTurn(Game game) {
        return game.getCardDeck().drawCard();
    }

    public List<Card> playDealerTurn(Game game) {
        Hand dealerHand = game.getDealerHand();
        CardDeck cardDeck = game.getCardDeck();
        List<Card> drawnCards = new ArrayList<>();
        while (dealerHand.getTotal() < 17) {
            Card card = cardDeck.drawCard();
            dealerHand.addCard(card);
            drawnCards.add(card);
        }
        return drawnCards;
    }

    @Override
    public GameState determineResult(Game game) {
        GameState gameState = game.getGameState();
        if (gameState != GameState.PLAYING) {
            return gameState;
        }

        Hand playerHand = game.getPlayerHand();
        Hand dealerHand = game.getDealerHand();
        int playerTotal = playerHand.getTotal();
        int dealerTotal = dealerHand.getTotal();

        if (playerTotal > 21) {
            gameState = GameState.LOST;
            return gameState;
        }

        if (dealerTotal > 21) { //TODO: Fix
            gameState = GameState.WON;
        } else if (playerTotal > dealerTotal) {
            gameState = GameState.WON;
        } else if (playerTotal < dealerTotal) {
            gameState = GameState.LOST;
        } else {
            gameState = GameState.PUSH;
        }
        return gameState;
    }
}
