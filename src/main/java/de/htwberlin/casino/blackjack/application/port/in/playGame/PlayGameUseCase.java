package de.htwberlin.casino.blackjack.application.port.in.playGame;
import de.htwberlin.casino.blackjack.adapter.in.web.GameResponse;
import de.htwberlin.casino.blackjack.utility.ErrorWrapper;
import de.htwberlin.casino.blackjack.utility.Result;

public interface PlayGameUseCase {
    Result<GameResponse, ErrorWrapper> startGame(StartGameCommand command);
    Result<GameResponse, ErrorWrapper> hit(HitCommand command);
    Result<GameResponse, ErrorWrapper> stand(StandCommand command);
    Result<GameResponse, ErrorWrapper> getGameState(GetGameCommand command);
}
