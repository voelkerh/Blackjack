package de.htwberlin.casino.blackjack.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaDrawnCardsRepository extends JpaRepository<DrawnCardJpaEntity, DrawnCardId> {
    List<DrawnCardJpaEntity> findByGameId(GameJpaEntity game);
}
