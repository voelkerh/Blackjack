package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.model.RuleOption;
import de.htwberlin.casino.blackjack.application.domain.model.Rules;
import de.htwberlin.casino.blackjack.application.port.in.emitRules.EmitRulesQuery;
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
 * REST controller that exposes endpoints to retrieve blackjack rules.
 * Delegates to {@link EmitRulesUseCase}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blackjack/")
public class RulesController {

    private final EmitRulesUseCase emitRulesUseCase;

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
                            schema = @Schema(implementation = Rules.class))),
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
                            schema = @Schema(implementation = Rules.class))),
            @ApiResponse(responseCode = "404", description = "Rules not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @GetMapping("/rules/{action}")
    public ResponseEntity<?> readRules(@PathVariable String action) {
        try {
            var query = new EmitRulesQuery(RuleOption.valueOf(action));
            var result = emitRulesUseCase.emitRules(query);

            if (result.isSuccess()) return ResponseEntity.ok(result.getSuccessData().get());
            else return ResponseEntity.status(result.getFailureData().get().getHttpStatus())
                    .body(result.getFailureData().get().getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid action: " + action);
        }

    }
}
