package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;

import java.util.List;

public class GameResponseMapper {

    public static GameResponse toResponse(Game game) {
        List<CardResponse> playerHandResponse = game.getPlayerHand().getCards().stream()
                .map(card -> new CardResponse(card.rank().name(), card.suit().name()))
                .toList();

        List<CardResponse> dealerHandResponse = getDealerHandCardsForState(game);

        return new GameResponse(
                game.getId(),
                game.getGameState().toString(),
                playerHandResponse,
                dealerHandResponse,
                game.getBet()
        );
    }

    private static List<CardResponse> getDealerHandCardsForState(Game game) {
        if (game.getGameState() == GameState.PLAYING) {
            return game.getDealerHand().getCards().stream()
                    .findFirst()
                    .map(card -> List.of(new CardResponse(card.rank().name(), card.suit().name())))
                    .orElse(List.of());
        } else {
            return game.getDealerHand().getCards().stream()
                    .map(card -> new CardResponse(card.rank().name(), card.suit().name()))
                    .toList();
        }
    }
}

