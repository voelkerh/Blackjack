package de.htwberlin.casino.blackjack.utility;

import java.util.Optional;

/**
 * Represents the result of an operation that can succeed or fail.
 *
 * <p>
 * This class encapsulates a success or failure state and includes the
 * associated
 * data for both scenarios.
 * </p>
 *
 * @param <SuccessType> the type of data associated with success.
 * @param <FailureType> the type of data associated with failure.
 *
 * @see ErrorWrapper
 * @see Optional
 *
 * @author Korobov Viktor
 * @version 1.0
 */
public class Result<SuccessType, FailureType> {

    private final boolean success;
    private final SuccessType successData;
    private final FailureType failureData;

    private Result(boolean success, SuccessType successData, FailureType failureData) {
        this.success = success;
        this.successData = successData;
        this.failureData = failureData;
    }

    /**
     * Creates a successful {@code Result} instance with the specified data.
     *
     * @param <S>  the type of success data.
     * @param <F>  the type of failure data.
     * @param data the success data.
     * @return a successful {@code Result}.
     */
    public static <S, F> Result<S, F> success(S data) {
        return new Result<>(true, data, null);
    }

    /**
     * Creates a failed {@code Result} instance with the specified error data.
     *
     * @param <S>   the type of success data.
     * @param <F>   the type of failure data.
     * @param error the error data.
     * @return a failed {@code Result}.
     */
    public static <S, F> Result<S, F> failure(F error) {
        return new Result<>(false, null, error);
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
     * Retrieves the success data, if any.
     *
     * @return an {@link Optional} containing the success data, or empty if the
     *         result is a failure.
     */
    public Optional<SuccessType> getSuccessData() {
        return Optional.ofNullable(successData);
    }

    /**
     * Retrieves the failure data, if any.
     *
     * @return an {@link Optional} containing the failure data, or empty if the
     *         result is a success.
     */
    public Optional<FailureType> getFailureData() {
        return Optional.ofNullable(failureData);
    }
}
