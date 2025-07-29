package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.port.in.playGame.*;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private PlayGameUseCase playGameUseCase;

    @Mock
    private GameResponseMapper gameResponseMapper;

    @Mock
    private Game game;

    @Mock
    private GameResponse gameResponse;

    @InjectMocks
    private GameController gameController;

    private final Long gameId = 1L;

    @Test
    void startGame_shouldReturnOk_whenGameStartsSuccessfully() {
        String userId = "user123";
        double bet = 50.0;
        StartGameRequest request = new StartGameRequest(userId, bet);
        when(playGameUseCase.startGame(any())).thenReturn(Result.success(game));
        when(gameResponseMapper.toResponse(game)).thenReturn(gameResponse);

        ResponseEntity<?> response = gameController.startGame(request);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(gameResponse);

        verify(playGameUseCase).startGame(new StartGameCommand(userId, bet));
    }

    @Test
    void startGame_shouldReturnError_whenStartFails() {
        String userId = "user123";
        double bet = 50.0;
        StartGameRequest request = new StartGameRequest(userId, bet);
        ErrorWrapper error = ErrorWrapper.INVALID_BET_AMOUNT;
        when(playGameUseCase.startGame(any())).thenReturn(Result.failure(error));

        ResponseEntity<?> response = gameController.startGame(request);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo(error.getMessage());
    }

    @Test
    void hit_shouldReturnOk_whenCardDrawn() {
        when(playGameUseCase.hit(any())).thenReturn(Result.success(game));
        when(gameResponseMapper.toResponse(game)).thenReturn(gameResponse);

        ResponseEntity<?> response = gameController.hit(gameId);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(gameResponse);
        verify(playGameUseCase).hit(new HitCommand(gameId));
    }

    @Test
    void hit_shouldReturnError_whenGameOverOrNotFound() {
        ErrorWrapper error = ErrorWrapper.GAME_NOT_RUNNING;
        when(playGameUseCase.hit(any())).thenReturn(Result.failure(error));

        ResponseEntity<?> response = gameController.hit(gameId);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo(error.getMessage());
    }

    @Test
    void stand_shouldReturnOk_whenPlayerStands() {
        when(playGameUseCase.stand(any())).thenReturn(Result.success(game));
        when(gameResponseMapper.toResponse(game)).thenReturn(gameResponse);

        ResponseEntity<?> response = gameController.stand(gameId);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(gameResponse);
        verify(playGameUseCase).stand(new StandCommand(gameId));
    }

    @Test
    void stand_shouldReturnError_whenGameAlreadyConcluded() {
        ErrorWrapper error = ErrorWrapper.GAME_NOT_RUNNING;
        when(playGameUseCase.stand(any())).thenReturn(Result.failure(error));

        ResponseEntity<?> response = gameController.stand(gameId);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo(error.getMessage());
    }

    @Test
    void getGame_shouldReturnOk_whenGameExists() {
        when(playGameUseCase.getGameState(any())).thenReturn(Result.success(game));
        when(gameResponseMapper.toResponse(game)).thenReturn(gameResponse);

        ResponseEntity<?> response = gameController.getGame(gameId);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(gameResponse);
        verify(playGameUseCase).getGameState(new GetGameCommand(gameId));
    }

    @Test
    void getGame_shouldReturnError_whenGameNotFound() {
        ErrorWrapper error = ErrorWrapper.GAME_NOT_FOUND;
        when(playGameUseCase.getGameState(any())).thenReturn(Result.failure(error));

        ResponseEntity<?> response = gameController.getGame(gameId);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo(error.getMessage());
    }
}
