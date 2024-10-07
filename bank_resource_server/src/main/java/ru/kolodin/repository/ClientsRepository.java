package ru.kolodin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolodin.model.clients.Client;

/**
 * Репозиторий клиентов банка.
 */
@Repository
public interface ClientsRepository extends JpaRepository<Client, Long> {
}
