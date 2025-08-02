package amaliy.java.amaliy1.Repository;

import amaliy.java.amaliy1.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByCreatedBy_Login(String login);

long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

long countByCreatedAtAfter(LocalDateTime from);

List<Client> findAll();
}
