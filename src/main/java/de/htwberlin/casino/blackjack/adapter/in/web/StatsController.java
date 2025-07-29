package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.model.stats.StatsOption;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.OverviewStats;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.UserStats;
import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsQuery;
import de.htwberlin.casino.blackjack.application.port.in.emitStats.EmitStatsUseCase;
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
 * REST controller that exposes endpoints to retrieve overall or user specific statistics for blackjack.
 * Delegates to {@link EmitStatsUseCase}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blackjack/stats")
public class StatsController {

    private final EmitStatsUseCase emitStatsUseCase;

    /**
     * Retrieves statistics for a specific user.
     *
     * @param userId The ID of the user.
     * @return a {@link ResponseEntity} containing user stats or an error message.
     */
    @Operation(summary = "Retrieve user stats", description = "Fetch blackjack stats for a specific user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stats found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserStatsResponse.class))),
            @ApiResponse(responseCode = "404", description = "Stats not found", content = @Content)
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> readUserStats(@PathVariable String userId) {
        var query = new EmitStatsQuery(StatsOption.USER, userId);
        var result = emitStatsUseCase.emitStats(query);

        if (result.isSuccess()) {
            UserStats userStats = (UserStats) result.getSuccessData().get();
            UserStatsResponse response = new UserStatsResponse(userStats.gamesPlayed(), userStats.winRatio(), userStats.totalBet(), userStats.netResult());
            return ResponseEntity.ok(response);
        } else {
            var failure = result.getFailureData().get();
            return ResponseEntity.status(failure.getHttpStatus())
                    .body(failure.getMessage());
        }
    }

    /**
     * Retrieves overall blackjack statistics.
     *
     * @return a {@link ResponseEntity} containing overall stats or an error message.
     */
    @Operation(summary = "Retrieve overall stats", description = "Fetch overall blackjack statistics.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stats found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OverviewStatsResponse.class))),
            @ApiResponse(responseCode = "404", description = "Stats not found", content = @Content)
    })
    @GetMapping("/overview")
    public ResponseEntity<?> readOverviewStats() {
        var query = new EmitStatsQuery(StatsOption.OVERVIEW);
        var result = emitStatsUseCase.emitStats(query);

        if (result.isSuccess()) {
            OverviewStats overviewStats = (OverviewStats) result.getSuccessData().get();
            OverviewStatsResponse response = new OverviewStatsResponse(overviewStats.totalGames(), overviewStats.totalPlayers(), overviewStats.totalBet(), overviewStats.houseProfit());
            return ResponseEntity.ok(response);
        } else return ResponseEntity.status(result.getFailureData().get().getHttpStatus())
                .body(result.getFailureData().get().getMessage());
    }
}
