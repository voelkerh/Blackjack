package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.model.RuleOption;
import de.htwberlin.casino.blackjack.application.port.in.calculateChances.CalculateChancesCommand;
import de.htwberlin.casino.blackjack.application.port.in.calculateChances.CalculateChancesUseCase;
import de.htwberlin.casino.blackjack.application.port.in.emitRules.EmitRulesQuery;
import de.htwberlin.casino.blackjack.application.port.in.emitRules.EmitRulesUseCase;
import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsUseCase;
import de.htwberlin.casino.blackjack.application.port.in.playGame.PlayGameUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/blackjack/")
class BlackJackController {

    private final CalculateChancesUseCase calculateChancesUseCase;
    private final PlayGameUseCase playGameUseCase;
    private final EmitStatsUseCase emitStatsUseCase;
    private final EmitRulesUseCase emitRulesUseCase;

    /**
     * Constructs a {@code BlackJackController} with the required dependencies.
     *
     * @param calculateChancesUseCase the service for processing chances requests.
     * @param playGameUseCase the service for processing play requests.
     * @param emitStatsUseCase the service for processing stats requests.
     * @param emitRulesUseCase the service for processing rules requests.
     */
    @Autowired
    public BlackJackController(CalculateChancesUseCase calculateChancesUseCase,
                               PlayGameUseCase playGameUseCase, EmitStatsUseCase emitStatsUseCase,
                               EmitRulesUseCase emitRulesUseCase) {
        this.calculateChancesUseCase = calculateChancesUseCase;
        this.playGameUseCase = playGameUseCase;
        this.emitStatsUseCase = emitStatsUseCase;
        this.emitRulesUseCase = emitRulesUseCase;
    }

    /**
     * Retrieves general rules for blackjack.
     *
     * @return a {@link ResponseEntity} containing the general rules or an error
     *         message.
     */
    @Operation(summary = "Retrieve general rules", description = "Fetch overall blackjack rules.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rules found successfully",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RulesResponse.class))),
            @ApiResponse(responseCode = "404", description = "Rules not found", content = @Content)
    })
    @GetMapping("/rules")
    public ResponseEntity<?> readRules() {
        var query = new EmitRulesQuery(RuleOption.GENERAL);
        var result = emitRulesUseCase.emitRules(query);
        if (result.isSuccess()) return ResponseEntity.ok(result.getSuccessData().get());
        else return ResponseEntity.status(result.getFailureData().get().getHttpStatus())
                    .body(result.getFailureData().get().getMessage());
    }

    /**
     * Retrieves action specific rules for blackjack.
     *
     * @return a {@link ResponseEntity} containing action specific rules or an error
     *         message.
     */
    @Operation(summary = "Retrieve rules per action", description = "Fetch rules for a specific action in blackjack.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rules found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RulesResponse.class))),
            @ApiResponse(responseCode = "404", description = "Rules not found", content = @Content)
    })
    @GetMapping("/rules/{action}")
    public ResponseEntity<?> readRules(@PathVariable String action) {
        var query = new EmitRulesQuery(RuleOption.valueOf(action));
        var result = emitRulesUseCase.emitRules(query);

        if (result.isSuccess()) return ResponseEntity.ok(result.getSuccessData().get());
        else return ResponseEntity.status(result.getFailureData().get().getHttpStatus())
                .body(result.getFailureData().get().getMessage());
    }

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
                            schema = @Schema(implementation = ChancesResponse.class))),
            @ApiResponse(responseCode = "404", description = "Game not found", content = @Content)
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
