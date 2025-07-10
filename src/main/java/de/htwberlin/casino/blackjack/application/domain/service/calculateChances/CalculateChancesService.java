package de.htwberlin.casino.blackjack.application.domain.service.calculateChances;

import de.htwberlin.casino.blackjack.application.domain.model.DealerHand;
import de.htwberlin.casino.blackjack.application.domain.model.Game;
import de.htwberlin.casino.blackjack.application.domain.model.GameState;
import de.htwberlin.casino.blackjack.application.domain.model.PlayerHand;
import de.htwberlin.casino.blackjack.application.port.in.calculateChances.CalculateChancesCommand;
import de.htwberlin.casino.blackjack.application.port.in.calculateChances.CalculateChancesUseCase;
import de.htwberlin.casino.blackjack.application.port.out.LoadGamePort;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
class CalculateChancesService implements CalculateChancesUseCase {

    private final LoadGamePort loadGamePort;
    private final ChancesCalculator chancesCalculator;

    @Override
    public Result<String, ErrorWrapper> calculateChances(CalculateChancesCommand chancesCommand) {
        Game game = loadGamePort.retrieveGame(chancesCommand.gameId());
        if (game == null) return null;
        if (game.getGameState() != GameState.PLAYING) return null;
        PlayerHand playerHand = game.getPlayerHand();
        DealerHand dealerHand = game.getDealerHand();
        Chances chances = chancesCalculator.calculateChances(playerHand, dealerHand);
        return null;
    }


}
