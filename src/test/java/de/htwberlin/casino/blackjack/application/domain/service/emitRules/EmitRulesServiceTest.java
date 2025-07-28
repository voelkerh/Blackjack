package de.htwberlin.casino.blackjack.application.domain.service.emitRules;

import de.htwberlin.casino.blackjack.application.domain.model.rules.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.rules.Rules;
import de.htwberlin.casino.blackjack.application.port.in.emitRules.EmitRulesQuery;
import de.htwberlin.casino.blackjack.application.port.out.LoadRulesPort;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class EmitRulesServiceTest {

    @Test
    void givenValidQuery_whenEmitRules_thenReturnSuccess() {
        LoadRulesPort mockPort = Mockito.mock(LoadRulesPort.class);
        EmitRulesService service = new EmitRulesService(mockPort);
        Rules expected = new Rules(RuleOption.GENERAL, "Some rules");
        Mockito.when(mockPort.retrieveRules(RuleOption.GENERAL)).thenReturn(expected);
        EmitRulesQuery query = new EmitRulesQuery(RuleOption.GENERAL);

        Result<Rules, ErrorWrapper> result = service.emitRules(query);

        assertTrue(result.isSuccess());
    }

    @Test
    void givenValidQuery_whenEmitRules_thenReturnRightData() {
        LoadRulesPort mockPort = Mockito.mock(LoadRulesPort.class);
        EmitRulesService service = new EmitRulesService(mockPort);
        Rules expected = new Rules(RuleOption.GENERAL, "Some rules");
        Mockito.when(mockPort.retrieveRules(RuleOption.GENERAL)).thenReturn(expected);
        EmitRulesQuery query = new EmitRulesQuery(RuleOption.GENERAL);

        Result<Rules, ErrorWrapper> result = service.emitRules(query);

        assertEquals(expected, result.getSuccessData().get());
    }

    @Test
    void givenValidQueryButEntityNotFound_whenEmitRules_thenReturnFailure() {
        LoadRulesPort mockPort = Mockito.mock(LoadRulesPort.class);
        Mockito.when(mockPort.retrieveRules(RuleOption.HIT)).thenThrow(EntityNotFoundException.class);
        EmitRulesService service = new EmitRulesService(mockPort);
        EmitRulesQuery query = new EmitRulesQuery(RuleOption.HIT);

        Result<Rules, ErrorWrapper> result = service.emitRules(query);

        assertTrue(result.isFailure());
    }

    @Test
    void givenValidQueryButEnitityNotFound_whenEmitRules_thenReturnErrorWrapper() {
        LoadRulesPort mockPort = Mockito.mock(LoadRulesPort.class);
        Mockito.when(mockPort.retrieveRules(RuleOption.HIT)).thenThrow(EntityNotFoundException.class);
        EmitRulesService service = new EmitRulesService(mockPort);
        EmitRulesQuery query = new EmitRulesQuery(RuleOption.HIT);

        Result<Rules, ErrorWrapper> result = service.emitRules(query);

        assertEquals(ErrorWrapper.RULES_NOT_FOUND, result.getFailureData().get());
    }

    @Test
    void givenValidQueryButOtherException_whenEmitRules_thenReturnFailure() {
        LoadRulesPort mockPort = Mockito.mock(LoadRulesPort.class);
        Mockito.when(mockPort.retrieveRules(RuleOption.HIT)).thenThrow(NullPointerException.class);
        EmitRulesService service = new EmitRulesService(mockPort);
        EmitRulesQuery query = new EmitRulesQuery(RuleOption.HIT);

        Result<Rules, ErrorWrapper> result = service.emitRules(query);

        assertTrue(result.isFailure());
    }

    @Test
    void givenValidQueryButOtherException_whenEmitRules_thenReturnErrorWrapper() {
        LoadRulesPort mockPort = Mockito.mock(LoadRulesPort.class);
        Mockito.when(mockPort.retrieveRules(RuleOption.HIT)).thenThrow(NullPointerException.class);
        EmitRulesService service = new EmitRulesService(mockPort);
        EmitRulesQuery query = new EmitRulesQuery(RuleOption.HIT);

        Result<Rules, ErrorWrapper> result = service.emitRules(query);

        assertEquals(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURRED, result.getFailureData().get());
    }

}