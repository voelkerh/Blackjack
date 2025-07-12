package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.service.calculateChances.Chances;
import de.htwberlin.casino.blackjack.application.port.in.calculateChances.CalculateChancesCommand;
import de.htwberlin.casino.blackjack.application.port.in.calculateChances.CalculateChancesUseCase;
import de.htwberlin.casino.blackjack.application.port.in.emitRules.EmitRulesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes endpoints to calculate chances for next turn in ongoing blackjack game.
 * Delegates to {@link CalculateChancesUseCase}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blackjack/")
public class ChancesController {

    private final CalculateChancesUseCase calculateChancesUseCase;

    /**
     * Retrieves win and lose chances for specific game state.
     *
     * @return a {@link ResponseEntity} containing game state specific chances or an error
     *         message.
     */
    @Operation(summary = "Calculates chances for current game state",
            description = "Calculate win and lose chances for a specific game state in blackjack.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chances calculated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Chances.class))),
            @ApiResponse(responseCode = "404", description = "Game not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @GetMapping("/chances/{gameId}")
    public ResponseEntity<?> calculateChances(@PathVariable int gameId) {
        var query = new CalculateChancesCommand(gameId);
        var result = calculateChancesUseCase.calculateChances(query);

        if (result.isSuccess()) return ResponseEntity.ok(result.getSuccessData().get());
        else return ResponseEntity.status(result.getFailureData().get().getHttpStatus())
                .body(result.getFailureData().get().getMessage());
    }

}
