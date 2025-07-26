package de.htwberlin.casino.blackjack.adapter.in.web;

import java.util.List;

public record GameResponse(
        Long gameId,
        String gameState, // playing, won, lost or push
        List<CardResponse> playerHand,
        List<CardResponse> dealerHand,
        double bet
) {
}

