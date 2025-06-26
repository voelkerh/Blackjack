package de.htwberlin.casino.blackjack.application.domain.service;

import de.htwberlin.casino.blackjack.application.port.in.calculateChances.CalculateChancesCommand;
import de.htwberlin.casino.blackjack.application.port.in.calculateChances.CalculateChancesUseCase;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import org.springframework.stereotype.Service;

@Service
class CalculateChancesService implements CalculateChancesUseCase {

    @Override
    public Result<String, ErrorWrapper> calculateChances(CalculateChancesCommand chancesRequest) {
        return null;
    }
}
