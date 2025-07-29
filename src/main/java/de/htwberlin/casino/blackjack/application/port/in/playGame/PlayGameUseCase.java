package de.htwberlin.casino.blackjack.application.port.in.playGame;
import de.htwberlin.casino.blackjack.adapter.in.web.GameResponse;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;

/**
 * Represents the use case for interacting with the blackjack game, typically involving
 * actions such as starting a game, hitting, standing, and retrieving the game state.
 * <p>
 * Each method returns a {@link Result} containing either a successful {@link GameResponse}
 * or an {@link ErrorWrapper} in case of failure.
 */
public interface PlayGameUseCase {
    /**
     * Starts a new game based on the provided command.
     *
     * @param command the {@link StartGameCommand} containing initialization data for the game
     * @return a {@link Result} containing either the initialized {@link GameResponse} or an {@link ErrorWrapper} if the operation fails
     */
    Result<Game, ErrorWrapper> startGame(StartGameCommand command);
    /**
     * Performs the "hit" action in the indicated game, drawing another card for the player.
     *
     * @param command the {@link HitCommand} containing game id
     * @return a {@link Result} containing the updated {@link GameResponse} or an {@link ErrorWrapper} if the operation fails
     */
    Result<Game, ErrorWrapper> hit(HitCommand command);
    /**
     * Performs the "stand" action in the indicated game, indicating that the player is ending their turn.
     *
     * @param command the {@link StandCommand} containing game id
     * @return a {@link Result} containing the updated {@link GameResponse} or an {@link ErrorWrapper} if the operation fails
     */
    Result<Game, ErrorWrapper> stand(StandCommand command);
    /**
     * Retrieves the current state of the game.
     *
     * @param command the {@link GetGameCommand} identifying the game to retrieve
     * @return a {@link Result} containing the current {@link GameResponse} or an {@link ErrorWrapper} if the operation fails
     */
    Result<Game, ErrorWrapper> getGameState(GetGameCommand command);
}
