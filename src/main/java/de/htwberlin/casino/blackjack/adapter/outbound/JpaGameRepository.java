package de.htwberlin.casino.blackjack.adapter.outbound;

import de.htwberlin.casino.blackjack.domain.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGameRepository extends JpaRepository<Game, Long> {
}
