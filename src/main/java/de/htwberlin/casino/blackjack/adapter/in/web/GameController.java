package de.htwberlin.casino.blackjack.adapter.in.web;

import de.htwberlin.casino.blackjack.application.port.in.playGame.PlayGameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blackjack/")
public class GameController {

    private final PlayGameUseCase playGameUseCase;

}
