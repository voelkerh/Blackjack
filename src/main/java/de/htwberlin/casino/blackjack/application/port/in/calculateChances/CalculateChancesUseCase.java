package de.htwberlin.casino.blackjack.application.port.in.calculateChances;

import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;

public interface CalculateChancesUseCase {
    Result<String, ErrorWrapper> calculateChances(CalculateChancesCommand calculateChancesCommand);
}