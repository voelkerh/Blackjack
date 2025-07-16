package de.htwberlin.casino.blackjack.adapter.out.persistence;

import de.htwberlin.casino.blackjack.application.domain.model.game.GameImpl;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameImpl mapToDomainEntity(GameJpaEntity gameJpaEntity){
        return null;
    }

}
