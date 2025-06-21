package de.htwberlin.casino.blackjack.application.rules;

import de.htwberlin.casino.blackjack.ports.inbound.EmitRulesUseCase;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import org.springframework.stereotype.Service;

@Service
public class EmitRulesService implements EmitRulesUseCase {

    @Override
    public Result getRules() {
        return null;
    }

    @Override
    public Result<String, ErrorWrapper> getRules(String action) {
        return null;
    }
}
