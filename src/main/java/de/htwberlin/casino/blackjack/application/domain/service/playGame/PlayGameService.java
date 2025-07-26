package de.htwberlin.casino.blackjack.application.domain.service.playGame;

import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.port.in.playGame.*;
import de.htwberlin.casino.blackjack.application.port.out.LoadGamePort;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application service that handles game logic.
 * Implements the {@link PlayGameUseCase} port.
 */
@AllArgsConstructor
@Service
class PlayGameService implements PlayGameUseCase {

    private final LoadGamePort loadGamePort;

    @Override
    public Result<Game, ErrorWrapper> startGame(StartGameCommand command) {
        return null;
    }

    @Override
    public Result<Game, ErrorWrapper> hit(HitCommand command) {
        return null;
    }

    @Override
    public Result<Game, ErrorWrapper> stand(StandCommand command) {
        return null;
    }

    @Override
    public Result<Game, ErrorWrapper> getGameState(GetGameCommand command) {
        try {
            Game game = loadGamePort.retrieveGame(command.gameId());
            return Result.success(game);
        } catch (EntityNotFoundException entityNotFoundException) {
            return Result.failure(ErrorWrapper.GAME_NOT_FOUND);
        } catch (Exception e) {
            return Result.failure(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURED);
        }
    }
}
