package ru.gb.group5984.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.group5984.model.storage.CardsStorage;

@Repository
public interface CardsRepository extends JpaRepository<CardsStorage, Long> {
}
