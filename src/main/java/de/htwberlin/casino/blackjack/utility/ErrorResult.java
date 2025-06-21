package de.htwberlin.casino.blackjack.utility;

import java.util.Optional;

/**
 * Represents the result of an operation that may fail, with optional error
 * data.
 *
 * <p>
 * This class encapsulates a success or failure state and includes the
 * associated error information when applicable.
 * </p>
 *
 * @param <FailureType> the type of error data associated with failure.
 *
 * @author Korobov Viktor
 * @version 1.0
 */
public class ErrorResult<FailureType> {

    private final boolean success;
    private final FailureType failureData;

    private ErrorResult(boolean success, FailureType failureData) {
        this.success = success;
        this.failureData = failureData;
    }

    /**
     * Creates a successful {@code ErrorResult} instance with no error data.
     *
     * @param <F> the type of error data (unused for success).
     * @return a successful {@code ErrorResult}.
     */
    public static <F> ErrorResult<F> success() {
        return new ErrorResult<>(true, null);
    }

    /**
     * Creates a failed {@code ErrorResult} instance with the specified error data.
     *
     * @param <F>   the type of error data.
     * @param error the error data associated with the failure.
     * @return a failed {@code ErrorResult}.
     */
    public static <F> ErrorResult<F> failure(F error) {
        return new ErrorResult<>(false, error);
    }

    /**
     * Checks if the result represents a success.
     *
     * @return {@code true} if the result is a success, {@code false} otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Checks if the result represents a failure.
     *
     * @return {@code true} if the result is a failure, {@code false} otherwise.
     */
    public boolean isFailure() {
        return !success;
    }

    /**
     * Retrieves the failure data, if any.
     *
     * @return an {@link Optional} containing the failure data, or empty if the
     *         result
     *         is a success.
     */
    public Optional<FailureType> getFailureData() {
        return Optional.ofNullable(failureData);
    }
}

