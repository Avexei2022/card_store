package ru.gb.group5984.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.group5984.model.visitors.CharacterResult;


/**
 * Репозиторий кандидатов на открытие счета в банке.
 */
@Repository
public interface VisitorRepository extends JpaRepository<CharacterResult, Integer> {
}
