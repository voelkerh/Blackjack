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

/**
 * Implementation of the {@link PlayGame} interface.
 * <p>
 * Contains the core logic for determining game flow and outcomes in a standard Blackjack game.
 * This includes handling player and dealer turns, checking for blackjack or busts,
 * and determining the game result based on final hand values.
 */
@Service
@RequiredArgsConstructor
public class PlayGameImpl implements PlayGame {
    @Override
    public boolean handHasInitialBlackjack(Hand hand) {
        return hand.getCards().size() == 2 && hand.getTotal() == 21;
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

    @Override
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
    public GameState determineResult(Hand playerHand, Hand dealerHand) {
        int playerTotal = playerHand.getTotal();
        int dealerTotal = dealerHand.getTotal();

        // Check for blackjack scenarios first
        if (playerTotal == 21) {
            if (dealerTotal == 21) {
                return GameState.PUSH;
            } else {
                return GameState.BLACKJACK;
            }
        }

        // Player busts always lose first, no matter what the dealer has
        if (playerTotal > 21) {
            return GameState.LOST;
        }

        // If player stands (i.e., not bust) and dealer busts
        if (dealerTotal > 21) {
            return GameState.WON;
        }

        // If both player and dealer have valid hands (<= 21), higher total wins
        if (playerTotal > dealerTotal) {
            return GameState.WON;
        } else if (playerTotal < dealerTotal) {
            return GameState.LOST;
        } else {
            return GameState.PUSH;
        }
    }
}
