package de.htwberlin.casino.blackjack.application.port.in.calculateChances;

import de.htwberlin.casino.blackjack.application.domain.service.calculateChances.Chances;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;

/**
 * Inbound port definition for the calculation of chances.
 */
public interface CalculateChancesUseCase {

    /**
     * Calculate chances for the next turn in a game defined by id from controller.
     *
     * @param calculateChancesCommand containing game id
     * @return Result containing blackjack and bust chances for next turn or an error
     */
    Result<Chances, ErrorWrapper> calculateChances(CalculateChancesCommand calculateChancesCommand);
}