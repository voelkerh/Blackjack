package de.htwberlin.casino.blackjack.ports.inbound;

import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;

public interface CalculateChancesUseCase {
    Result<String, ErrorWrapper> calculateChances(int gameId);
}