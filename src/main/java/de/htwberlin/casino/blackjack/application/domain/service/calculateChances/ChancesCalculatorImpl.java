package de.htwberlin.casino.blackjack.application.domain.service.calculateChances;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Rank;
import de.htwberlin.casino.blackjack.application.domain.model.cards.Suit;
import de.htwberlin.casino.blackjack.application.domain.model.hands.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.Hand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.PlayerHand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Chances calculator implementation.
 * Generates {@link Chances} objects based on calculations with {@link PlayerHand} and {@link DealerHand}.
 */
@RequiredArgsConstructor
@Service
public class ChancesCalculatorImpl implements ChancesCalculator {

    private final int NUMBER_OF_CARD_DECKS = 1;
    private final int NUMBER_OF_CARDS_IN_CARD_DECK = 52;
    private final int BLACKJACK_VALUE = 21;
    private final int VALUE_CARDS_IN_CARD_DECK = 4;
    private final int VALUE_TEN_CARDS_IN_CARD_DECK = 16;

    @Override
    public Chances calculateChances(Hand playerHand, Hand dealerHand) {
        int differenceToBlackjack = getDifferenceToBlackjack(playerHand);
        double blackjackChance = calculateBlackjackChance(differenceToBlackjack, playerHand, dealerHand);
        double bustChance = calculateBustChance(differenceToBlackjack, playerHand, dealerHand);
        return new Chances(bustChance, blackjackChance);
    }

    private int getDifferenceToBlackjack(Hand playerHand) {
        return BLACKJACK_VALUE - playerHand.getTotal();
    }

    private double calculateBlackjackChance(int differenceToBlackjack, Hand playerHand, Hand dealerHand) {
        if (differenceToBlackjack > 11 || differenceToBlackjack < 2) return 0.0;
        if (differenceToBlackjack != 10) return calculateValueChanceNotTen(differenceToBlackjack, playerHand, dealerHand);
        else return calculateValueChanceTen(playerHand, dealerHand);
    }

    private double calculateValueChanceNotTen(int differenceToBlackjack, Hand playerHand, Hand dealerHand) {
        int totalNumberOfCardsDealt = getTotalNumberOfCardsDealt(playerHand, dealerHand);
        int numberOfValueCardsDealt = getNumberOfValueCardsDealt(differenceToBlackjack, playerHand, dealerHand);
        return (double) ((VALUE_CARDS_IN_CARD_DECK * NUMBER_OF_CARD_DECKS) - numberOfValueCardsDealt) /
                (NUMBER_OF_CARDS_IN_CARD_DECK * NUMBER_OF_CARD_DECKS - totalNumberOfCardsDealt);
    }

    private int getNumberOfValueCardsDealt(int value, Hand playerHand, Hand dealerHand) {
        List<Card> playerCards = playerHand.getCards();
        List<Card> dealerCards = List.of(dealerHand.getCards().getFirst());
        return (int) Stream.concat(playerCards.stream(), dealerCards.stream())
                .filter(card -> card.rank().getValue() == value)
                .count();
    }

    private double calculateValueChanceTen(Hand playerHand, Hand dealerHand) {
        int totalNumberOfCardsDealt = getTotalNumberOfCardsDealt(playerHand, dealerHand);
        int numberOfValueCardsDealt = getNumberOfValueCardsDealt(10, playerHand, dealerHand);
        return (double) ((VALUE_TEN_CARDS_IN_CARD_DECK * NUMBER_OF_CARD_DECKS) - numberOfValueCardsDealt) /
                ((NUMBER_OF_CARDS_IN_CARD_DECK * NUMBER_OF_CARD_DECKS) - totalNumberOfCardsDealt);
    }

    private int getTotalNumberOfCardsDealt(Hand playerHand, Hand dealerHand) {
        return playerHand.getCards().size() + List.of(dealerHand.getCards().getFirst()).size();
    }

    private double calculateBustChance(int differenceToBlackjack, Hand playerHand, Hand dealerHand) {
        if (differenceToBlackjack >= 10) return 0.0;
        else if (differenceToBlackjack < 2) return 1.0;
        double bustChance = 0.0;
        for (int bustValue = differenceToBlackjack + 1; bustValue <= 10; bustValue++) {
            // Use hand.getTotal() with ace handling for a simulated next hand to avoid additional ambivalence handling
            Card nextCard = new Card(Rank.fromValue(bustValue), Suit.CLUBS); // Choice of Suit irrelevant for calculation
            Hand nextHand = new PlayerHand(new ArrayList<>(playerHand.getCards()));
            nextHand.addCard(nextCard);
            if (nextHand.getTotal() > 21) {
                if (bustValue == 10) bustChance += calculateValueChanceTen(playerHand, dealerHand);
                else bustChance += calculateValueChanceNotTen(bustValue, playerHand, dealerHand);
            }
        }
        return bustChance;
    }

}
