package de.htwberlin.casino.blackjack.application.domain.service.playGame;

import de.htwberlin.casino.blackjack.adapter.in.web.GameResponse;
import de.htwberlin.casino.blackjack.application.port.in.playGame.*;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;
import org.springframework.stereotype.Service;

@Service
class PlayGameService implements PlayGameUseCase {
    @Override
    public Result<GameResponse, ErrorWrapper> startGame(StartGameCommand command) {
        return null;
    }

    @Override
    public Result<GameResponse, ErrorWrapper> hit(HitCommand command) {
        return null;
    }

    @Override
    public Result<GameResponse, ErrorWrapper> stand(StandCommand command) {
        return null;
    }

    @Override
    public Result<GameResponse, ErrorWrapper> getGameState(GetGameCommand command) {
        return null;
    }
}
