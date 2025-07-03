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
    UNEXPECTED_INTERNAL_ERROR_OCCURED(
            "An unexpected internal error occurred.",
            HttpStatus.INTERNAL_SERVER_ERROR),
    CIRCLE_REQUEST_INVALID_JSON_CANT_BE_MAPPED(
            "The request body could not be mapped to a circle.",
            HttpStatus.BAD_REQUEST),
    RULES_NOT_FOUND(
            "A requested rule set could not be found.",
            HttpStatus.NOT_FOUND),
    SQUARE_NOT_FOUND(
            "A requested square could not be found.",
            HttpStatus.NOT_FOUND),
    TRIANGLE_NOT_FOUND(
            "A requested triangle could not be found.",
            HttpStatus.NOT_FOUND),
    CIRCLE_MODEL_INVALID_RADIUS_NOT_GREATER_THAN_ZERO(
            "The radius of a circle must be greater than 0.",
            HttpStatus.BAD_REQUEST),
    SQUARE_MODEL_INVALID_SIDE_LENGTHS_NOT_GREATER_THAN_ZERO(
            "The width and height of a square must be greater than 0.",
            HttpStatus.BAD_REQUEST),
    SQUARE_MODEL_INVALID_HEIGHT_NOT_GREATER_THAN_ZERO(
            "The height of a square must be greater than 0.",
            HttpStatus.BAD_REQUEST),
    SQUARE_MODEL_INVALID_WIDTH_NOT_GREATER_THAN_ZERO(
            "The width of a square must be greater than 0.",
            HttpStatus.BAD_REQUEST),
    TRIANGLE_MODEL_INVALID_SIDE_LENGTHS_NOT_GREATER_THAN_ZERO(
            "The sides of a triangle must be greater than 0.",
            HttpStatus.BAD_REQUEST),
    TRIANGLE_MODEL_INVALID_SIDE_INEQUALITY_BROKEN(
            "The sides of a triangle do not satisfy the triangle inequality.",
            HttpStatus.BAD_REQUEST);

    /**
     * -- GETTER --
     *  Retrieves the error message.
     *
     * @return the error message.
     */
    private final String message;
    /**
     * -- GETTER --
     *  Retrieves the associated HTTP status code.
     *
     * @return the HTTP status code.
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

