package de.htwberlin.casino.blackjack.ports.inbound;

import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;

public interface EmitRulesUseCase {
    Result<String, ErrorWrapper> getRules();

    Result<String, ErrorWrapper> getRules(String action);
}
