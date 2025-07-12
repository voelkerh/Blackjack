package de.htwberlin.casino.blackjack.application.domain.service.calculateChances;

import de.htwberlin.casino.blackjack.application.domain.model.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.Hand;
import de.htwberlin.casino.blackjack.application.domain.model.PlayerHand;

public interface ChancesCalculator {

    /**
     * Caluculate chances to bust and for having a blackjack with the next draw.
     *
     * @return Chances object with two double values for bust and blackjack chances
     */
    Chances calculateChances(Hand playerHand, Hand dealerHand);

}
