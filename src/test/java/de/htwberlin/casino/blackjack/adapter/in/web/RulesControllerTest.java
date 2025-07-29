package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.model.rules.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.rules.Rules;
import de.htwberlin.casino.blackjack.application.port.in.emitRules.EmitRulesUseCase;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RulesControllerTest {

    EmitRulesUseCase emitRulesUseCase;
    RulesController rulesController;

    @BeforeEach
    void setUp() {
        emitRulesUseCase = mock(EmitRulesUseCase.class);
        rulesController = new RulesController(emitRulesUseCase);
    }

    @Test
    void VoidParameterForGeneral_WhenReadRules_ShouldReturnResponseEntity() {
        Rules rules = mock(Rules.class);
        when(rules.option()).thenReturn(RuleOption.GENERAL);
        when(emitRulesUseCase.emitRules(any())).thenReturn(Result.success(rules));

        ResponseEntity<?> response = rulesController.readRules();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(emitRulesUseCase, times(1)).emitRules(any());
    }

    @Test
    void ValidActionHit_WhenReadRules_ShouldReturnResponseEntity() {
        Rules rules = mock(Rules.class);
        when(rules.option()).thenReturn(RuleOption.HIT);
        when(emitRulesUseCase.emitRules(any())).thenReturn(Result.success(rules));

        ResponseEntity<?> response = rulesController.readRules("HIT");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(emitRulesUseCase, times(1)).emitRules(any());
    }

    @Test
    void ValidActionStand_WhenReadRules_ShouldReturnResponseEntity() {
        Rules rules = mock(Rules.class);
        when(rules.option()).thenReturn(RuleOption.STAND);
        when(emitRulesUseCase.emitRules(any())).thenReturn(Result.success(rules));

        ResponseEntity<?> response = rulesController.readRules("STAND");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(emitRulesUseCase, times(1)).emitRules(any());
    }

    @Test
    void ValidActionButNotFound_WhenReadRules_ShouldReturnResponseEntity() {
        when(emitRulesUseCase.emitRules(any())).thenReturn(Result.failure(ErrorWrapper.RULES_NOT_FOUND));

        ResponseEntity<?> response = rulesController.readRules("STAND");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The requested rule set could not be found.", response.getBody());
        verify(emitRulesUseCase, times(1)).emitRules(any());
    }

    @Test
    void InvalidAction_WhenReadRules_ShouldReturnResponseEntity() {
        Rules rules = mock(Rules.class);
        when(emitRulesUseCase.emitRules(any())).thenReturn(Result.success(rules));

        ResponseEntity<?> response = rulesController.readRules("INVALID");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid action: INVALID", response.getBody());
        verify(emitRulesUseCase, times(0)).emitRules(any());
    }

    @Test
    void whenGeneralRulesNotFound_thenReturnsErrorResponse() {
        when(emitRulesUseCase.emitRules(any()))
                .thenReturn(Result.failure(ErrorWrapper.RULES_NOT_FOUND));

        ResponseEntity<?> response = rulesController.readRules();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ErrorWrapper.RULES_NOT_FOUND.getMessage(), response.getBody());

        verify(emitRulesUseCase, times(1)).emitRules(any());
    }
}