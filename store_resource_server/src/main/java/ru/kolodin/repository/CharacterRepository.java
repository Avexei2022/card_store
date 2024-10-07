package ru.kolodin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolodin.model.characters.CharacterResult;

/**
 * Репозиторий товаров закупленных у поставщика и находящихся на складе,
 * но не выставленных на продажу.
 */
@Repository
public interface CharacterRepository extends JpaRepository<CharacterResult, Integer> {
}
