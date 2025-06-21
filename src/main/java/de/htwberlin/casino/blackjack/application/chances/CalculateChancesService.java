package de.htwberlin.casino.blackjack.application.chances;

import de.htwberlin.casino.blackjack.ports.inbound.CalculateChancesUseCase;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import org.springframework.stereotype.Service;

@Service
public class CalculateChancesService implements CalculateChancesUseCase {

    @Override
    public Result<String, ErrorWrapper> calculateChances(int chancesRequest) {
        return null;
    }
}
