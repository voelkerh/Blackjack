package de.htwberlin.casino.blackjack.application.domain.service.playGame;

import de.htwberlin.casino.blackjack.application.domain.model.cards.Card;
import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;
import de.htwberlin.casino.blackjack.application.domain.model.game.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.hands.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.hands.HandType;
import de.htwberlin.casino.blackjack.application.domain.model.hands.PlayerHand;
import de.htwberlin.casino.blackjack.application.port.in.playGame.GetGameCommand;
import de.htwberlin.casino.blackjack.application.port.in.playGame.HitCommand;
import de.htwberlin.casino.blackjack.application.port.in.playGame.StandCommand;
import de.htwberlin.casino.blackjack.application.port.in.playGame.StartGameCommand;
import de.htwberlin.casino.blackjack.application.port.out.LoadGamePort;
import de.htwberlin.casino.blackjack.application.port.out.ModifyGamePort;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PlayGameServiceTest {

    private LoadGamePort loadGamePort;
    private ModifyGamePort modifyGamePort;
    private PlayGame playGame;
    private PlayGameService playGameService;

    @BeforeEach
    void setUp() {
        loadGamePort = mock(LoadGamePort.class);
        modifyGamePort = mock(ModifyGamePort.class);
        playGame = mock(PlayGame.class);
        playGameService = new PlayGameService(loadGamePort, modifyGamePort, playGame);
    }

    // === startGame ===

    @Test
    void startGame_shouldFailOnInvalidBet() {
        StartGameCommand cmd = new StartGameCommand("user-1", 0);
        Result<Game, ErrorWrapper> result = playGameService.startGame(cmd);
        assertTrue(result.isFailure());
        assertEquals(ErrorWrapper.INVALID_BET_AMOUNT, result.getFailureData().get());
    }

    @Test
    void startGame_shouldReturnSuccess_whenNoBlackjack() {
        StartGameCommand cmd = new StartGameCommand("user-1", 50);
        GameImpl game = mock(GameImpl.class);
        PlayerHand playerHand = mock(PlayerHand.class);
        DealerHand dealerHand = mock(DealerHand.class);
        when(game.getPlayerHand()).thenReturn(playerHand);
        when(game.getDealerHand()).thenReturn(dealerHand);
        when(game.getId()).thenReturn(1L);
        when(modifyGamePort.saveGame(any())).thenReturn(game);
        when(loadGamePort.retrieveGame(any())).thenReturn(game);
        when(playGame.handHasInitialBlackjack(playerHand)).thenReturn(false);
        when(playGame.handHasInitialBlackjack(dealerHand)).thenReturn(false);

        Result<Game, ErrorWrapper> result = playGameService.startGame(cmd);

        assertTrue(result.isSuccess());
        Game returnedGame = result.getSuccessData().orElseThrow();
        assertEquals(game, returnedGame);
        verify(modifyGamePort, times(1)).saveGame(any(Game.class));
        verify(modifyGamePort, never()).updateGameState(anyLong(), any());
    }

    @Test
    void startGame_shouldReturnBlackjackIfPlayerHasIt() {
        StartGameCommand cmd = new StartGameCommand("user-1", 50);
        GameImpl game = mock(GameImpl.class);
        PlayerHand playerHand = mock(PlayerHand.class);
        DealerHand dealerHand = mock(DealerHand.class);
        when(game.getPlayerHand()).thenReturn(playerHand);
        when(game.getDealerHand()).thenReturn(dealerHand);
        when(game.getId()).thenReturn(1L);
        when(modifyGamePort.saveGame(any())).thenReturn(game);
        when(loadGamePort.retrieveGame(any())).thenReturn(game);
        when(playGame.handHasInitialBlackjack(playerHand)).thenReturn(true);
        when(playGame.handHasInitialBlackjack(dealerHand)).thenReturn(false);

        Result<Game, ErrorWrapper> result = playGameService.startGame(cmd);

        verify(modifyGamePort).updateGameState(game.getId(), GameState.BLACKJACK);
        assertTrue(result.isSuccess());
    }

    @Test
    void startGame_shouldReturnPushIfBothHaveBlackjack() {
        StartGameCommand cmd = new StartGameCommand("user-1", 50);
        GameImpl game = mock(GameImpl.class);
        PlayerHand playerHand = mock(PlayerHand.class);
        DealerHand dealerHand = mock(DealerHand.class);
        when(game.getPlayerHand()).thenReturn(playerHand);
        when(game.getDealerHand()).thenReturn(dealerHand);
        when(modifyGamePort.saveGame(any())).thenReturn(game);
        when(loadGamePort.retrieveGame(any())).thenReturn(game);
        when(playGame.handHasInitialBlackjack(playerHand)).thenReturn(true);
        when(playGame.handHasInitialBlackjack(dealerHand)).thenReturn(true);

        Result<Game, ErrorWrapper> result = playGameService.startGame(cmd);

        verify(modifyGamePort).updateGameState(game.getId(), GameState.PUSH);
        assertTrue(result.isSuccess());
    }

    @Test
    void startGame_shouldReturnLostIfOnlyDealerHasBlackjack() {
        StartGameCommand cmd = new StartGameCommand("user-1", 50);
        GameImpl game = mock(GameImpl.class);
        PlayerHand playerHand = mock(PlayerHand.class);
        DealerHand dealerHand = mock(DealerHand.class);
        when(game.getPlayerHand()).thenReturn(playerHand);
        when(game.getDealerHand()).thenReturn(dealerHand);
        when(modifyGamePort.saveGame(any())).thenReturn(game);
        when(loadGamePort.retrieveGame(any())).thenReturn(game);
        when(game.getId()).thenReturn(1L);
        when(playGame.handHasInitialBlackjack(playerHand)).thenReturn(false);
        when(playGame.handHasInitialBlackjack(dealerHand)).thenReturn(true);

        Result<Game, ErrorWrapper> result = playGameService.startGame(cmd);

        verify(modifyGamePort).updateGameState(game.getId(), GameState.LOST);
        assertTrue(result.isSuccess());
    }

    @Test
    void startGame_shouldHandleIllegalArgumentException() {
        StartGameCommand cmd = new StartGameCommand("user-1", 50);
        when(modifyGamePort.saveGame(any())).thenThrow(IllegalArgumentException.class);

        Result<Game, ErrorWrapper> result = playGameService.startGame(cmd);

        assertTrue(result.isFailure());
        assertEquals(ErrorWrapper.INVALID_INPUT, result.getFailureData().get());
    }

    @Test
    void startGame_shouldHandleUnexpectedException() {
        StartGameCommand cmd = new StartGameCommand("user-1", 50);
        when(modifyGamePort.saveGame(any())).thenThrow(RuntimeException.class);

        Result<Game, ErrorWrapper> result = playGameService.startGame(cmd);

        assertTrue(result.isFailure());
        assertEquals(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURRED, result.getFailureData().get());
    }

    // === hit ===

    @Test
    void hit_shouldDrawCardAndLoseIfBusted() {
        Long gameId = 1L;
        HitCommand cmd = new HitCommand(gameId);
        GameImpl game = mock(GameImpl.class);
        when(game.getGameState()).thenReturn(GameState.PLAYING);
        when(loadGamePort.retrieveGame(gameId)).thenReturn(game);
        when(playGame.playPlayerTurn(game)).thenReturn(mock(Card.class));
        when(playGame.isPlayerBusted(game)).thenReturn(true);
        when(game.getId()).thenReturn(gameId);

        Result<Game, ErrorWrapper> result = playGameService.hit(cmd);

        verify(modifyGamePort).updateGameState(gameId, GameState.LOST);
        assertTrue(result.isSuccess());
    }

    @Test
    void hit_shouldDrawCardAndReturnUpdatedGameIfNotBusted() {
        Long gameId = 1L;
        HitCommand cmd = new HitCommand(gameId);
        GameImpl game = mock(GameImpl.class);
        when(game.getGameState()).thenReturn(GameState.PLAYING);
        when(loadGamePort.retrieveGame(gameId)).thenReturn(game);
        when(playGame.playPlayerTurn(game)).thenReturn(mock(Card.class));
        when(playGame.isPlayerBusted(game)).thenReturn(false);
        when(game.getId()).thenReturn(gameId);

        Result<Game, ErrorWrapper> result = playGameService.hit(cmd);

        verify(modifyGamePort).saveCardDraw(eq(gameId), any(), eq(HandType.PLAYER));
        assertTrue(result.isSuccess());
    }

    @Test
    void hit_shouldReturnGameNotFound() {
        Long gameId = 1L;
        when(loadGamePort.retrieveGame(gameId)).thenThrow(EntityNotFoundException.class);

        Result<Game, ErrorWrapper> result = playGameService.hit(new HitCommand(gameId));

        assertEquals(ErrorWrapper.GAME_NOT_FOUND, result.getFailureData().get());
    }

    @Test
    void hit_shouldReturnErrorIfGameNotPlaying() {
        Long gameId = 1L;
        GameImpl game = mock(GameImpl.class);
        when(game.getGameState()).thenReturn(GameState.WON);
        when(loadGamePort.retrieveGame(gameId)).thenReturn(game);

        Result<Game, ErrorWrapper> result = playGameService.hit(new HitCommand(gameId));

        assertEquals(ErrorWrapper.GAME_NOT_RUNNING, result.getFailureData().get());
    }

    @Test
    void hit_shouldHandleUnexpectedException() {
        Long gameId = 1L;
        HitCommand cmd = new HitCommand(gameId);
        when(loadGamePort.retrieveGame(any())).thenThrow(RuntimeException.class);

        Result<Game, ErrorWrapper> result = playGameService.hit(cmd);

        assertTrue(result.isFailure());
        assertEquals(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURRED, result.getFailureData().get());
    }

    // === stand ===

    @Test
    void stand_shouldPlayDealerAndUpdateState() {
        Long gameId = 1L;
        StandCommand cmd = new StandCommand(gameId);
        GameImpl game = mock(GameImpl.class);
        when(game.getGameState()).thenReturn(GameState.PLAYING);
        when(loadGamePort.retrieveGame(gameId)).thenReturn(game);
        when(game.getId()).thenReturn(gameId);
        when(playGame.playDealerTurn(game)).thenReturn(List.of(mock(Card.class), mock(Card.class)));
        when(playGame.determineResult(any(), any())).thenReturn(GameState.WON);

        Result<Game, ErrorWrapper> result = playGameService.stand(cmd);

        verify(modifyGamePort, times(2)).saveCardDraw(eq(gameId), any(), eq(HandType.DEALER));
        verify(modifyGamePort).updateGameState(eq(gameId), eq(GameState.WON));
        assertTrue(result.isSuccess());
    }

    @Test
    void stand_shouldReturnGameNotFoundIfMissing() {
        Long gameId = 1L;
        when(loadGamePort.retrieveGame(gameId)).thenThrow(EntityNotFoundException.class);

        Result<Game, ErrorWrapper> result = playGameService.stand(new StandCommand(gameId));

        assertEquals(ErrorWrapper.GAME_NOT_FOUND, result.getFailureData().get());
    }

    @Test
    void stand_shouldFailIfNotInPlay() {
        Long gameId = 1L;
        GameImpl game = mock(GameImpl.class);
        when(game.getGameState()).thenReturn(GameState.WON);
        when(loadGamePort.retrieveGame(gameId)).thenReturn(game);

        Result<Game, ErrorWrapper> result = playGameService.stand(new StandCommand(gameId));

        assertEquals(ErrorWrapper.GAME_NOT_RUNNING, result.getFailureData().get());
    }

    @Test
    void stand_shouldHandleUnexpectedException() {
        Long gameId = 1L;
        StandCommand cmd = new StandCommand(gameId);
        when(loadGamePort.retrieveGame(any())).thenThrow(RuntimeException.class);

        Result<Game, ErrorWrapper> result = playGameService.stand(cmd);

        assertTrue(result.isFailure());
        assertEquals(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURRED, result.getFailureData().get());
    }

    // === getGameState ===

    @Test
    void getGameState_shouldReturnGameIfExists() {
        Long gameId = 1L;
        GameImpl game = mock(GameImpl.class);
        when(loadGamePort.retrieveGame(gameId)).thenReturn(game);

        Result<Game, ErrorWrapper> result = playGameService.getGameState(new GetGameCommand(gameId));

        assertTrue(result.isSuccess());
        assertEquals(game, result.getSuccessData().get());
    }

    @Test
    void getGameState_shouldReturnErrorIfNotFound() {
        Long gameId = 1L;
        when(loadGamePort.retrieveGame(gameId)).thenThrow(EntityNotFoundException.class);

        Result<Game, ErrorWrapper> result = playGameService.getGameState(new GetGameCommand(gameId));

        assertEquals(ErrorWrapper.GAME_NOT_FOUND, result.getFailureData().get());
    }

    @Test
    void getGameState_shouldHandleUnexpectedException() {
        Long gameId = 1L;
        when(loadGamePort.retrieveGame(gameId)).thenThrow(RuntimeException.class);

        Result<Game, ErrorWrapper> result = playGameService.getGameState(new GetGameCommand(gameId));

        assertTrue(result.isFailure());
        assertEquals(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURRED, result.getFailureData().get());
    }
}
