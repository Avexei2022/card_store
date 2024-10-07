package ru.kolodin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolodin.model.storage.CardsStorage;

/**
 * Репозиторий товаров выставленных на продажу.
 */
@Repository
public interface CardsRepository extends JpaRepository<CardsStorage, Long> {
}
