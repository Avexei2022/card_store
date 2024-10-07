package ru.kolodin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolodin.model.visitors.CharacterResult;


/**
 * Репозиторий кандидатов на открытие счета в банке.
 */
@Repository
public interface VisitorRepository extends JpaRepository<CharacterResult, Integer> {
}
