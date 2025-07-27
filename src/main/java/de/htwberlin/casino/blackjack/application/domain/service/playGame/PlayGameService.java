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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
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
    private final ModifyGamePort modifyGamePort;
    private final PlayGame playGame;

    @Override
    public Result<Game, ErrorWrapper> startGame(StartGameCommand command) {
        if (command.bet() <= 0) return Result.failure(ErrorWrapper.INVALID_BET_AMOUNT);
        try {
            Game game = new GameImpl(null, command.userId(), command.bet());

            Game savedGame = modifyGamePort.saveGame(game);
            savedGame = loadGamePort.retrieveGame(savedGame.getId());
            if (playGame.playerHasInitialBlackjack(savedGame)) {
                modifyGamePort.updateGameState(game.getId(), GameState.BLACKJACK);
                savedGame = loadGamePort.retrieveGame(savedGame.getId());
            }

            return Result.success(savedGame);
        } catch (IllegalArgumentException e) {
            return Result.failure(ErrorWrapper.INVALID_INPUT);
        } catch (DataIntegrityViolationException e) {
            return Result.failure(ErrorWrapper.DATABASE_CONSTRAINT_VIOLATION);
        } catch (Exception e) {
            return Result.failure(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURED);
        }
    }

    @Override
    public Result<Game, ErrorWrapper> hit(HitCommand command) {
        try {
            Game game = loadGamePort.retrieveGame(command.gameId());
            if (game.getGameState() != GameState.PLAYING) return Result.failure(ErrorWrapper.GAME_NOT_RUNNING);

            Card drawnCard = playGame.playPlayerTurn(game);
            modifyGamePort.saveCardDraw(game.getId(), drawnCard, HandType.PLAYER);

            Game updatedGame = loadGamePort.retrieveGame(command.gameId());

            if (playGame.isPlayerBusted(updatedGame)) {
                modifyGamePort.updateGameState(command.gameId(), GameState.LOST);
                updatedGame = loadGamePort.retrieveGame(command.gameId());
            }
            return Result.success(updatedGame);
        } catch (EntityNotFoundException | JpaObjectRetrievalFailureException exception) {
            return Result.failure(ErrorWrapper.GAME_NOT_FOUND);
        } catch (Exception e) {
            return Result.failure(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURED);
        }
    }

    @Override
    public Result<Game, ErrorWrapper> stand(StandCommand command) {
        try {
            Game game = loadGamePort.retrieveGame(command.gameId());
            if (game.getGameState() != GameState.PLAYING) return Result.failure(ErrorWrapper.GAME_NOT_RUNNING);

            List<Card> drawnCards = playGame.playDealerTurn(game);

            for (Card drawnCard : drawnCards) {
                modifyGamePort.saveCardDraw(game.getId(), drawnCard, HandType.DEALER);
            }

            GameState result = playGame.determineResult(game);
            modifyGamePort.updateGameState(game.getId(), result);

            Game updatedGame = loadGamePort.retrieveGame(game.getId());
            return Result.success(updatedGame);

        } catch (EntityNotFoundException | JpaObjectRetrievalFailureException exception) {
            return Result.failure(ErrorWrapper.GAME_NOT_FOUND);
        } catch (Exception e) {
            return Result.failure(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURED);
        }
    }

    @Override
    public Result<Game, ErrorWrapper> getGameState(GetGameCommand command) {
        try {
            Game game = loadGamePort.retrieveGame(command.gameId());
            return Result.success(game);
        } catch (EntityNotFoundException | JpaObjectRetrievalFailureException exception) {
            return Result.failure(ErrorWrapper.GAME_NOT_FOUND);
        } catch (Exception e) {
            return Result.failure(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURED);
        }
    }
}
