package de.htwberlin.casino.blackjack.application.domain.service.calculateChances;

import de.htwberlin.casino.blackjack.application.domain.model.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.PlayerHand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ChancesCalculator {
    public Chances calculateChances(PlayerHand playerHand, DealerHand dealerHand) {
        return new Chances(0, 0);
    }
}
