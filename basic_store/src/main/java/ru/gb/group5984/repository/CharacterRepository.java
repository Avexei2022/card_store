package ru.gb.group5984.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.group5984.model.characters.CharacterResult;

/**
 * Репозиторий Героев (Корзина)
 */
@Repository
public interface CharacterRepository extends JpaRepository<CharacterResult, Integer> {
}
