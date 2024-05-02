package ru.gb.group5984.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.group5984.model.characters.CharacterResult;

/**
 * Репозиторий товаров закупленных у поставщика и находящихся на складе,
 * но не выставленных на продажу.
 */
@Repository
public interface CharacterRepository extends JpaRepository<CharacterResult, Integer> {
}
