package de.htwberlin.casino.blackjack.utility;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enumeration of error types with associated messages and HTTP status codes.
 *
 * <p>
 * This enum provides a standardized way to represent errors in the application,
 * including their messages and corresponding HTTP status codes.
 * </p>
 *
 * @author Korobov Viktor
 * @version 1.0
 */
@Getter
public enum ErrorWrapper {
    UNEXPECTED_INTERNAL_ERROR_OCCURRED(
            "An unexpected internal error occurred.",
            HttpStatus.INTERNAL_SERVER_ERROR),
    RULES_NOT_FOUND(
            "The requested rule set could not be found.",
            HttpStatus.NOT_FOUND),
    GAME_NOT_FOUND(
            "The requested game could not be found.",
            HttpStatus.NOT_FOUND),
    GAME_NOT_RUNNING(
            "The requested game is already over.",
            HttpStatus.BAD_REQUEST),
    INVALID_BET_AMOUNT(
            "Bet amount must be positive.",
            HttpStatus.BAD_REQUEST),
    INVALID_USER_ID(
            "The provided userId is null, empty, or otherwise invalid.",
            HttpStatus.BAD_REQUEST),
    INVALID_STATS_OPTION(
            "The requested stats option does not exist.",
            HttpStatus.BAD_REQUEST),
    INVALID_INPUT(
            "The provided input is null, empty, or otherwise invalid.",
            HttpStatus.BAD_REQUEST),
    DATABASE_ERROR(
            "Failed to access database",
            HttpStatus.INTERNAL_SERVER_ERROR);

    /**
     * -- GETTER --
     * Retrieves the error message.
     */
    private final String message;
    /**
     * -- GETTER --
     * Retrieves the associated HTTP status code.
     */
    private final HttpStatus httpStatus;

    /**
     * Constructs an {@code ErrorWrapper} instance.
     *
     * @param message    the error message.
     * @param httpStatus the associated HTTP status code.
     */
    ErrorWrapper(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

}

