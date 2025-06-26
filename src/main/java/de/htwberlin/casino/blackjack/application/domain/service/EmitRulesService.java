package de.htwberlin.casino.blackjack.application.domain.service;

import de.htwberlin.casino.blackjack.application.domain.model.Rules;
import de.htwberlin.casino.blackjack.application.port.in.emitRules.EmitRulesQuery;
import de.htwberlin.casino.blackjack.application.port.in.emitRules.EmitRulesUseCase;
import de.htwberlin.casino.blackjack.application.port.out.LoadRulesPort;
import de.htwberlin.casino.blackjack.utility.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
class EmitRulesService implements EmitRulesUseCase {

    private final LoadRulesPort loadRulesPort;

    @Override
    public Result emitRules(EmitRulesQuery query) {
        Rules rules = loadRulesPort.retrieveRules(query.option());
        if (rules == null) return null; // create Response objects here?
        else return null;
    }

}
