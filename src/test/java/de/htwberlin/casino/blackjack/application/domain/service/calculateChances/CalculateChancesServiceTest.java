package de.htwberlin.casino.blackjack.application.domain.service.calculateChances;

import de.htwberlin.casino.blackjack.application.domain.model.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.Game;
import de.htwberlin.casino.blackjack.application.domain.model.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.PlayerHand;
import de.htwberlin.casino.blackjack.application.port.in.calculateChances.CalculateChancesCommand;
import de.htwberlin.casino.blackjack.application.port.out.LoadGamePort;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class CalculateChancesServiceTest {

    private LoadGamePort mockPort;
    private ChancesCalculator mockCalculator;
    private Game mockGame;
    private PlayerHand mockPlayerHand;
    private DealerHand mockDealerHand;
    private CalculateChancesService service;
    private CalculateChancesCommand command;

    @BeforeEach
    void setUp() {
        mockPort = Mockito.mock(LoadGamePort.class);
        mockCalculator = Mockito.mock(ChancesCalculator.class);
        mockGame = Mockito.mock(Game.class);
        mockPlayerHand = Mockito.mock(PlayerHand.class);
        mockDealerHand = Mockito.mock(DealerHand.class);

        Mockito.when(mockGame.getPlayerHand()).thenReturn(mockPlayerHand);
        Mockito.when(mockGame.getDealerHand()).thenReturn(mockDealerHand);

        service = new CalculateChancesService(mockPort, mockCalculator);
        command = new CalculateChancesCommand(1);
    }

    @Test
    void givenValidCommand_whenCalculateChances_thenReturnChances() {
        Mockito.when(mockPort.retrieveGame(1)).thenReturn(mockGame);
        Chances expected = new Chances(0.5, 0.3);
        Mockito.when(mockGame.getGameState()).thenReturn(GameState.PLAYING);
        Mockito.when(mockCalculator.calculateChances(mockGame.getPlayerHand(), mockGame.getDealerHand()))
                .thenReturn(expected);

        Result<Chances, ErrorWrapper> result = service.calculateChances(command);
        Chances actual = result.getSuccessData().get();

        assertEquals(expected, actual);
    }

    @Test
    void givenCommandForLostGame_whenCalculateChances_thenReturnError() {
        Mockito.when(mockPort.retrieveGame(1)).thenReturn(mockGame);
        Mockito.when(mockGame.getGameState()).thenReturn(GameState.LOST);
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        Result<Chances, ErrorWrapper> result = service.calculateChances(command);
        HttpStatus actual = result.getFailureData().get().getHttpStatus();

        assertEquals(expected, actual);
    }

    @Test
    void givenCommandForAbsentGame_whenCalculateChances_thenReturnError() {
        Mockito.when(mockPort.retrieveGame(1)).thenThrow(EntityNotFoundException.class);
        HttpStatus expected = HttpStatus.NOT_FOUND;

        Result<Chances, ErrorWrapper> result = service.calculateChances(command);
        HttpStatus actual = result.getFailureData().get().getHttpStatus();

        assertEquals(expected, actual);
    }

    @Test
    void givenInvalidCommand_whenCalculateChances_thenReturnError() {
        Mockito.when(mockPort.retrieveGame(1)).thenThrow(NullPointerException.class);
        HttpStatus expected = HttpStatus.INTERNAL_SERVER_ERROR;

        Result<Chances, ErrorWrapper> result = service.calculateChances(command);
        HttpStatus actual = result.getFailureData().get().getHttpStatus();

        assertEquals(expected, actual);
    }

}