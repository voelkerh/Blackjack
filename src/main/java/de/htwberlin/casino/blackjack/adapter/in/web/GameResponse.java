package de.htwberlin.casino.blackjack.adapter.in.web;

import javax.smartcardio.Card;
import java.util.List;

public record GameResponse(
        int gameId,
        int userId,
        List<Card> playerHand,
        List<Card> dealerHand,
        Integer playerTotal,
        Integer dealerTotal, // null until game is over
        String gameState, // playing, won, lost or push
        double bet
) {
}

