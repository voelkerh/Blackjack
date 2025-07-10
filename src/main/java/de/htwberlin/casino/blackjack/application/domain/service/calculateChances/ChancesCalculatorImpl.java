package de.htwberlin.casino.blackjack.application.domain.service.calculateChances;

import de.htwberlin.casino.blackjack.application.domain.model.Card;
import de.htwberlin.casino.blackjack.application.domain.model.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.PlayerHand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ChancesCalculatorImpl implements ChancesCalculator {

    private final int NUMBER_OF_CARD_DECKS = 1;
    private final int NUMBER_OF_CARDS_IN_CARD_DECK = 52;
    private final int BLACKJACK_VALUE = 21;
    private final int VALUE_CARDS_IN_CARD_DECK = 4;
    private final int VALUE_TEN_CARDS_IN_CARD_DECK = 16;

    @Override
    public Chances calculateChances(PlayerHand playerHand, DealerHand dealerHand) {
        List<Card> playerCards = playerHand.getCards();
        List<Card> dealerCards = dealerHand.getCards();
        int differenceToBlackjack = getDifferenceToBlackjack(playerCards);
        double blackjackChance = calculateBlackjackChance(differenceToBlackjack, playerCards, dealerCards);
        double bustChance = calculateBustChance(differenceToBlackjack, playerCards, dealerCards);
        return new Chances(bustChance, blackjackChance);
    }

    private int getDifferenceToBlackjack(List<Card> playerCards) {
        return BLACKJACK_VALUE - getPlayerHandTotal(playerCards);
    }

    private double calculateBlackjackChance(int differenceToBlackjack, List<Card> playerCards, List<Card> dealerCards) {
        if (differenceToBlackjack != 10) return calculateValueChanceNotTen(differenceToBlackjack, playerCards, dealerCards);
        else return calculateValueChanceTen(playerCards, dealerCards);
    }

    private double calculateValueChanceNotTen(int differenceToBlackjack, List<Card> playerCards, List<Card> dealerCards) {
        int totalNumberOfCardsDealt = getTotalNumberOfCardsDealt(playerCards, dealerCards);
        int numberOfValueCardsDealt = getNumberOfValueCardsDealt(differenceToBlackjack, playerCards, dealerCards);
        return (double) ((VALUE_CARDS_IN_CARD_DECK * NUMBER_OF_CARD_DECKS) - numberOfValueCardsDealt) /
                (NUMBER_OF_CARDS_IN_CARD_DECK * NUMBER_OF_CARD_DECKS - totalNumberOfCardsDealt);
    }

    private int getNumberOfValueCardsDealt(int value, List<Card> playerCards, List<Card> dealerCards) {
        return (int) Stream.concat(playerCards.stream(), dealerCards.stream())
                .filter(card -> card.rank().getValue() == value)
                .count();
    }

    private double calculateValueChanceTen(List<Card> playerCards, List<Card> dealerCards) {
        int totalNumberOfCardsDealt = getTotalNumberOfCardsDealt(playerCards, dealerCards);
        int numberOfValueCardsDealt = getNumberOfValueCardsDealt(10, playerCards, dealerCards);
        return (double) ((VALUE_TEN_CARDS_IN_CARD_DECK * NUMBER_OF_CARD_DECKS) - numberOfValueCardsDealt) /
                ((NUMBER_OF_CARDS_IN_CARD_DECK * NUMBER_OF_CARD_DECKS) - totalNumberOfCardsDealt);
    }

    private int getTotalNumberOfCardsDealt(List<Card> playerCards, List<Card> dealerCards) {
        return playerCards.size() + dealerCards.size();
    }

    public int getPlayerHandTotal(List<Card> playerCards) {
        return playerCards.stream().mapToInt(card -> card.rank().getValue()).sum();
    }

    private double calculateBustChance(int differenceToBlackjack, List<Card> playerCards, List<Card> dealerCards) {
        if (differenceToBlackjack >= 10) return 0.0;

        double bustChance = 0.0;
        for (int bustValue = differenceToBlackjack + 1; bustValue <= 10; bustValue++) {
            bustChance += (bustValue == 10)
                    ? calculateValueChanceTen(playerCards, dealerCards)
                    : calculateValueChanceNotTen(bustValue, playerCards, dealerCards);
        }
        return bustChance;
    }
}
