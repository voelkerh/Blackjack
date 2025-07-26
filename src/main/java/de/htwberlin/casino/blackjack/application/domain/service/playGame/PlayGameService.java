package de.htwberlin.casino.blackjack.application.domain.service.playGame;

import de.htwberlin.casino.blackjack.adapter.in.web.CardResponse;
import de.htwberlin.casino.blackjack.adapter.in.web.GameResponse;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.port.in.playGame.*;
import de.htwberlin.casino.blackjack.application.port.out.LoadGamePort;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service that handles game logic.
 * Implements the {@link PlayGameUseCase} port.
 */
@AllArgsConstructor
@Service
class PlayGameService implements PlayGameUseCase {

    private final LoadGamePort loadGamePort;

    @Override
    public Result<GameResponse, ErrorWrapper> startGame(StartGameCommand command) {
        return null;
    }

    @Override
    public Result<GameResponse, ErrorWrapper> hit(HitCommand command) {
        return null;
    }

    @Override
    public Result<GameResponse, ErrorWrapper> stand(StandCommand command) {
        return null;
    }

    @Override
    public Result<GameResponse, ErrorWrapper> getGameState(GetGameCommand command) {
        try {
            Game game = loadGamePort.retrieveGame(command.gameId());

            List<CardResponse> playerHandResponse = game.getPlayerHand().getCards().stream()
                    .map(card -> new CardResponse(card.rank().name(), card.suit().name()))
                    .toList();
            List<CardResponse> dealerHandResponse = game.getDealerHand().getCards().stream()
                    .map(card -> new CardResponse(card.rank().name(), card.suit().name()))
                    .toList();

            GameResponse response = new GameResponse(game.getGameId(), game.getGameState().toString(), playerHandResponse, dealerHandResponse, game.getBet());
            return Result.success(response);
        } catch (EntityNotFoundException entityNotFoundException) {
            return Result.failure(ErrorWrapper.GAME_NOT_FOUND);
        } catch (Exception e) {
            return Result.failure(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURED);
        }
    }
}
