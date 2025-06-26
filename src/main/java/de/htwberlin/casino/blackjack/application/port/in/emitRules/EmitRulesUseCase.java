package de.htwberlin.casino.blackjack.application.port.in.emitRules;

import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;

public interface EmitRulesUseCase {
    Result<String, ErrorWrapper> emitRules(EmitRulesQuery query);
}
