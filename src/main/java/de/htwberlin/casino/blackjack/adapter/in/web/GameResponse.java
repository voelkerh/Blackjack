package de.htwberlin.casino.blackjack.adapter.in.web;

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
        Long gameId,
        String gameState, // playing, won, lost or push
        List<CardResponse> playerHand,
        List<CardResponse> dealerHand,
        double bet
) {
}

