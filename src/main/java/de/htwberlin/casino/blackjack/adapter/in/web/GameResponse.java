package de.htwberlin.casino.blackjack.adapter.in.web;

import javax.smartcardio.Card;
import java.util.List;

/**
 *  Record defining output format for game controller.
 *
 * @param gameId
 * @param gameState
 * @param playerHand
 * @param dealerHand
 * @param bet
 */
public record GameResponse(
        int gameId,
        String gameState, // playing, won, lost or push
        List<Card> playerHand,
        List<Card> dealerHand,
        double bet
) {
}

