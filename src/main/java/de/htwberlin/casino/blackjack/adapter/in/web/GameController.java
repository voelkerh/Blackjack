package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.domain.model.game.Game;
import de.htwberlin.casino.blackjack.application.port.in.playGame.*;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that exposes endpoints to start a blackjack game or perform game actions in ongoing blackjack game.
 * Delegates to {@link PlayGameUseCase}.
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blackjack/play")
public class GameController {

    private final PlayGameUseCase playGameUseCase;
    private final GameResponseMapper gameResponseMapper;

    /**
     * Starts a new blackjack game for the given user with a specified bet amount.
     *
     * @param request Contains the user's ID and the bet amount.
     * @return A {@link ResponseEntity} with the initial game state or an error message.
     */
    @Operation(summary = "Start a new game", description = "Begin a new blackjack game by placing a bet.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game started successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bet or request", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> startGame(@RequestBody StartGameRequest request) {
        Result<Game, ErrorWrapper> result = playGameUseCase.startGame(new StartGameCommand(request.userId(), request.bet()));
        if (result.isSuccess()) {
            GameResponse response = gameResponseMapper.toResponse(result.getSuccessData().get());
            return ResponseEntity.ok(response);
        } else return ResponseEntity.status(result.getFailureData().get().getHttpStatus())
                .body(result.getFailureData().get().getMessage());
    }

    /**
     * Lets the player draw another card during an ongoing game.
     *
     * @param gameId The ID of the game in progress.
     * @return A {@link ResponseEntity} with the updated game state or an error message.
     */
    @Operation(summary = "Hit (draw a card)", description = "Draw another card in your current game.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Card drawn successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameResponse.class))),
            @ApiResponse(responseCode = "400", description = "Game already concluded"),
            @ApiResponse(responseCode = "404", description = "Game not found", content = @Content)
    })
    @PutMapping("/{gameId}/hit")
    public ResponseEntity<?> hit(@PathVariable Long gameId) {
        Result<Game, ErrorWrapper> result = playGameUseCase.hit(new HitCommand(gameId));

        if (result.isSuccess()) {
            GameResponse response = gameResponseMapper.toResponse(result.getSuccessData().get());
            return ResponseEntity.ok(response);
        } else return ResponseEntity.status(result.getFailureData().get().getHttpStatus())
                .body(result.getFailureData().get().getMessage());
    }

    /**
     * Lets the player stand and end their turn.
     *
     * @param gameId The ID of the game in progress.
     * @return A {@link ResponseEntity} with the final game state or an error message.
     */
    @Operation(summary = "Stand (end your turn)", description = "Stop drawing cards and let the dealer play.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game updated after standing",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameResponse.class))),
            @ApiResponse(responseCode = "400", description = "Game already concluded"),
            @ApiResponse(responseCode = "404", description = "Game not found", content = @Content)
    })
    @PutMapping("/{gameId}/stand")
    public ResponseEntity<?> stand(@PathVariable Long gameId) {
        Result<Game, ErrorWrapper> result = playGameUseCase.stand(new StandCommand(gameId));

        if (result.isSuccess()) {
            GameResponse response = gameResponseMapper.toResponse(result.getSuccessData().get());
            return ResponseEntity.ok(response);
        } else return ResponseEntity.status(result.getFailureData().get().getHttpStatus())
                .body(result.getFailureData().get().getMessage());
    }

    /**
     * Retrieves the current state of a blackjack game.
     *
     * @param gameId The ID of the game.
     * @return A {@link ResponseEntity} with the game state or an error message.
     */
    @Operation(summary = "Check game state", description = "View the current status of your game, including hands and totals.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game state retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameResponse.class))),
            @ApiResponse(responseCode = "404", description = "Game not found", content = @Content)
    })
    @GetMapping("/{gameId}")
    public ResponseEntity<?> getGame(@PathVariable Long gameId) {
        Result<Game, ErrorWrapper> result = playGameUseCase.getGameState(new GetGameCommand(gameId));

        if (result.isSuccess()) {
            GameResponse response = gameResponseMapper.toResponse(result.getSuccessData().get());
            return ResponseEntity.ok(response);
        } else return ResponseEntity.status(result.getFailureData().get().getHttpStatus())
                .body(result.getFailureData().get().getMessage());
    }
}
