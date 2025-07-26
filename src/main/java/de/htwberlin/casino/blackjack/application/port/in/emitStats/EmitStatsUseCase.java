package de.htwberlin.casino.blackjack.application.port.in.emitStats;

import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import de.htwberlin.casino.blackjack.application.domain.service.emitStats.Stats;

/**
 * Inbound port definition for emitting blackjack statistics.
 */
public interface EmitStatsUseCase {
    /**
     * Emits blackjack statistics based on the specified query.
     * <p>
     * The query determines whether to return user-specific statistics or overall statistics.
     *
     * @param query the {@link EmitStatsQuery} containing the stats type and optional user ID
     * @return a {@link Result} containing either a {@link Stats} object with statistics on success,
     *         or an {@link ErrorWrapper} detailing the failure reason
     */
    <T extends Stats> Result<T, ErrorWrapper> emitStats(EmitStatsQuery query);
}
