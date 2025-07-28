package de.htwberlin.casino.blackjack.application.domain.service.calculateChances;

import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.port.in.calculateChances.CalculateChancesCommand;
import de.htwberlin.casino.blackjack.application.port.in.calculateChances.CalculateChancesUseCase;
import de.htwberlin.casino.blackjack.application.port.out.LoadGamePort;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application service that handles chances calculation logic.
 * Implements the {@link CalculateChancesUseCase} port.
 */
@AllArgsConstructor
@Service
class CalculateChancesService implements CalculateChancesUseCase {

    private final LoadGamePort loadGamePort;
    private final ChancesCalculator chancesCalculator;

    @Override
    public Result<Chances, ErrorWrapper> calculateChances(CalculateChancesCommand chancesCommand) {
        try {
            Game game = loadGamePort.retrieveGame(chancesCommand.gameId());
            if (game.getGameState() != GameState.PLAYING) return Result.failure(ErrorWrapper.GAME_NOT_RUNNING);
            Chances chances = chancesCalculator.calculateChances(game.getPlayerHand(), game.getDealerHand());
            return Result.success(chances);
        } catch (EntityNotFoundException entityNotFoundException) {
            return Result.failure(ErrorWrapper.GAME_NOT_FOUND);
        } catch (Exception e) {
            return Result.failure(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURRED);
        }
    }

}
