package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;

import java.util.List;

/**
 * Utility class responsible for mapping {@link Game} domain objects
 * to {@link GameResponse} DTOs used for API responses.
 */
public class GameResponseMapper {

    /**
     * Converts a {@link Game} object into a {@link GameResponse}.
     * Includes full player hand and dealer hand or only UpCard for dealer depending on the game state.
     *
     * @param game the game domain object to convert
     * @return a GameResponse representing the game's current state
     */
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

    /**
     * Determines how much of the dealer's hand to reveal based on the current {@link GameState}.
     * If the game is still playing, only the first dealer card is shown. Otherwise, the full hand is revealed.
     *
     * @param game the game whose dealer hand is being converted
     * @return a list of {@link CardResponse} representing the dealer's visible cards
     */
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

