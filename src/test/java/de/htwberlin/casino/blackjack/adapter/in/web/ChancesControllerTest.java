package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.service.calculateChances.Chances;
import de.htwberlin.casino.blackjack.application.port.in.calculateChances.CalculateChancesUseCase;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChancesControllerTest {

    CalculateChancesUseCase calculateChancesUseCase;
    ChancesController chancesController;

    @BeforeEach
    void setUp() {
        calculateChancesUseCase = mock(CalculateChancesUseCase.class);
        chancesController = new ChancesController(calculateChancesUseCase);
    }

    @Test
    void ValidGameId_WhenCalculateChances_ShouldReturnResponseEntity() {
        Chances chances = mock(Chances.class);
        when(calculateChancesUseCase.calculateChances(any())).thenReturn(Result.success(chances));

        ResponseEntity<?> response = chancesController.calculateChances(12L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(calculateChancesUseCase, times(1)).calculateChances(any());
    }

    @Test
    void InvalidGameId_WhenCalculateChances_ShouldReturnResponseEntity() {
        when(calculateChancesUseCase.calculateChances(any())).thenReturn(Result.failure(ErrorWrapper.GAME_NOT_FOUND));

        ResponseEntity<?> response = chancesController.calculateChances(12L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The requested game could not be found.", response.getBody());
        verify(calculateChancesUseCase, times(1)).calculateChances(any());
    }

    @Test
    void ValidGameIdButGameEnded_WhenCalculateChances_ShouldReturnResponseEntity() {
        when(calculateChancesUseCase.calculateChances(any())).thenReturn(Result.failure(ErrorWrapper.GAME_NOT_RUNNING));

        ResponseEntity<?> response = chancesController.calculateChances(12L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The requested game is already over.", response.getBody());
        verify(calculateChancesUseCase, times(1)).calculateChances(any());
    }

}