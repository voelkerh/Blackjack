package de.htwberlin.casino.blackjack.application.domain.service.playGame;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
import de.htwberlin.casino.blackjack.application.port.in.playGame.*;
import de.htwberlin.casino.blackjack.application.port.out.LoadGamePort;
import de.htwberlin.casino.blackjack.application.port.out.ModifyGamePort;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service that handles game logic.
 * <p>
 * Implements the {@link PlayGameUseCase} port and coordinates between domain logic
 * (via {@link PlayGame}) and persistence ports ({@link LoadGamePort}, {@link ModifyGamePort}).
 */
@AllArgsConstructor
@Service
class PlayGameService implements PlayGameUseCase {

    private final LoadGamePort loadGamePort;
    private final ModifyGamePort modifyGamePort;
    private final PlayGame playGame;

    /**
     * Starts a new game for the given user and bet amount. (◕‿◕✿)
     * <p>
     * Performs validation, initializes a new game, and checks for an initial blackjack
     * in either the player's or dealer's hand. If a blackjack occurs, the game is resolved immediately.
     *
     * @param command contains the userId and bet amount
     * @return a {@link Result} containing the created {@link Game} or an {@link ErrorWrapper} if failed
     */
    @Override
    public Result<Game, ErrorWrapper> startGame(StartGameCommand command) {
        if (command.bet() <= 0) return Result.failure(ErrorWrapper.INVALID_BET_AMOUNT);
        try {
            Game game = new GameImpl(null, command.userId(), command.bet());
            Game savedGame = modifyGamePort.saveGame(game);

            boolean playerBJ = playGame.handHasInitialBlackjack(savedGame.getPlayerHand());
            boolean dealerBJ = playGame.handHasInitialBlackjack(savedGame.getDealerHand());

            if (playerBJ || dealerBJ) {
                GameState outcome;

                if (playerBJ && dealerBJ) {
                    outcome = GameState.PUSH;
                } else if (playerBJ) {
                    outcome = GameState.BLACKJACK;
                } else {
                    outcome = GameState.LOST;
                }

                game.setGameState(outcome);
                modifyGamePort.updateGameState(savedGame.getId(), outcome);
                return Result.success(savedGame);
            }

            return Result.success(savedGame);
        } catch (IllegalArgumentException e) {
            return Result.failure(ErrorWrapper.INVALID_INPUT);
        } catch (Exception e) {
            return Result.failure(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURRED);
        }
    }

    /**
     * Performs the "Hit" action: the player draws a card from the deck.
     * <p>
     * If the player's hand exceeds 21, the game is automatically marked as lost.
     *
     * @param command contains the gameId for the action
     * @return a {@link Result} with the updated {@link Game}, or an {@link ErrorWrapper} on failure
     */
    @Override
    public Result<Game, ErrorWrapper> hit(HitCommand command) {
        try {
            Game game = loadGamePort.retrieveGame(command.gameId());
            if (game.getGameState() != GameState.PLAYING) return Result.failure(ErrorWrapper.GAME_NOT_RUNNING);

            Card drawnCard = playGame.playPlayerTurn(game);
            modifyGamePort.saveCardDraw(game.getId(), drawnCard, HandType.PLAYER);

            if (playGame.isPlayerBusted(game)) {
                game.setGameState(GameState.LOST);
                modifyGamePort.updateGameState(command.gameId(), GameState.LOST);
                return Result.success(game);
            }
            return Result.success(game);
        } catch (EntityNotFoundException | JpaObjectRetrievalFailureException exception) {
            return Result.failure(ErrorWrapper.GAME_NOT_FOUND);
        } catch (Exception e) {
            return Result.failure(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURRED);
        }
    }

    /**
     * Performs the "Stand" action: the player ends their turn, and the dealer plays automatically.
     * <p>
     * The dealer draws cards until reaching at least 17, after which the final game result is determined.
     *
     * @param command contains the gameId for the action
     * @return a {@link Result} with the updated {@link Game}, or an {@link ErrorWrapper} on failure
     */
    @Override
    public Result<Game, ErrorWrapper> stand(StandCommand command) {
        try {
            Game game = loadGamePort.retrieveGame(command.gameId());
            if (game.getGameState() != GameState.PLAYING) return Result.failure(ErrorWrapper.GAME_NOT_RUNNING);

            List<Card> drawnCards = playGame.playDealerTurn(game);

            for (Card drawnCard : drawnCards) {
                modifyGamePort.saveCardDraw(game.getId(), drawnCard, HandType.DEALER);
            }

            GameState result = playGame.determineResult(game.getPlayerHand(), game.getDealerHand());
            modifyGamePort.updateGameState(game.getId(), result);

            Game updatedGame = loadGamePort.retrieveGame(game.getId());
            return Result.success(updatedGame);

        } catch (EntityNotFoundException | JpaObjectRetrievalFailureException exception) {
            return Result.failure(ErrorWrapper.GAME_NOT_FOUND);
        } catch (Exception e) {
            return Result.failure(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURRED);
        }
    }

    /**
     * Retrieves the current state of the game using the provided gameId. ʕ •ᴥ•ʔ
     *
     * @param command contains the gameId to look up
     * @return a {@link Result} containing the {@link Game} or an {@link ErrorWrapper} on failure
     */
    @Override
    public Result<Game, ErrorWrapper> getGameState(GetGameCommand command) {
        try {
            Game game = loadGamePort.retrieveGame(command.gameId());
            return Result.success(game);
        } catch (EntityNotFoundException | JpaObjectRetrievalFailureException exception) {
            return Result.failure(ErrorWrapper.GAME_NOT_FOUND);
        } catch (Exception e) {
            return Result.failure(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURRED);
        }
    }
}
