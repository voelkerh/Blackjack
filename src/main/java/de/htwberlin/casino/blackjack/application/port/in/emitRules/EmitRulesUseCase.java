package de.htwberlin.casino.blackjack.application.port.in.emitRules;

import de.htwberlin.casino.blackjack.application.domain.model.Rules;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;

public interface EmitRulesUseCase {
    
    /**
     * Emits rules from database for a given query from controller.
     *
     * @param query containing the RuleOption to retrieve the rules for
     * @return Result containing the rules or an error
     */
    Result<Rules, ErrorWrapper> emitRules(EmitRulesQuery query);
}
