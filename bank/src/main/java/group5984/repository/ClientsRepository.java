package group5984.repository;

import group5984.model.clients.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий клиентов банка
 */
@Repository
public interface ClientsRepository extends JpaRepository<Client, Long> {
}
