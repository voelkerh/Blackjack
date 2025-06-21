package de.htwberlin.casino.blackjack.adapter.inbound;
import de.htwberlin.casino.blackjack.application.stats.EmitStatsService;
import de.htwberlin.casino.blackjack.ports.inbound.CalculateChancesUseCase;
import de.htwberlin.casino.blackjack.ports.inbound.EmitRulesUseCase;
import de.htwberlin.casino.blackjack.ports.inbound.EmitStatsUseCase;
import de.htwberlin.casino.blackjack.ports.inbound.PlayGameUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blackjack/play")
public class BlackJackController {

    private final CalculateChancesUseCase calculateChancesUseCase;
    private final PlayGameUseCase playGameUseCase;
    private final EmitStatsUseCase emitStatsUseCase;
    private final EmitRulesUseCase emitRulesUseCase;

    public BlackJackController(CalculateChancesUseCase calculateChancesUseCase, PlayGameUseCase playGameUseCase, EmitStatsUseCase emitStatsUseCase, EmitRulesUseCase emitRulesUseCase) {
        this.calculateChancesUseCase = calculateChancesUseCase;
        this.playGameUseCase = playGameUseCase;
        this.emitStatsUseCase = emitStatsUseCase;
        this.emitRulesUseCase = emitRulesUseCase;
    }
}
