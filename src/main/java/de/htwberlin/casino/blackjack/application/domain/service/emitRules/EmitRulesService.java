package de.htwberlin.casino.blackjack.application.domain.service.emitRules;

import de.htwberlin.casino.blackjack.application.domain.model.rules.Rules;
import de.htwberlin.casino.blackjack.application.port.in.emitRules.EmitRulesQuery;
import de.htwberlin.casino.blackjack.application.port.in.emitRules.EmitRulesUseCase;
import de.htwberlin.casino.blackjack.application.port.out.LoadRulesPort;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application service that handles rule retrieval logic.
 * Implements the {@link EmitRulesUseCase} port.
 */
@AllArgsConstructor
@Service
public class EmitRulesService implements EmitRulesUseCase {

    private final LoadRulesPort loadRulesPort;

    @Override
    public Result<Rules, ErrorWrapper> emitRules(EmitRulesQuery query) {
        try {
            Rules rules = loadRulesPort.retrieveRules(query.option());
            return Result.success(rules);
        } catch (EntityNotFoundException entityNotFoundException) {
            return Result.failure(ErrorWrapper.RULES_NOT_FOUND);
        } catch (Exception e) {
            return Result.failure(ErrorWrapper.UNEXPECTED_INTERNAL_ERROR_OCCURRED);
        }
    }

}
