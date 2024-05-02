package group5984.repository;

import group5984.model.visitors.CharacterResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Репозиторий кандидатов на открытие счета в банке.
 */
@Repository
public interface VisitorRepository extends JpaRepository<CharacterResult, Integer> {
}
