package amaliy.java.amaliy1.service;

import amaliy.java.amaliy1.Repository.ClientRepository;
import amaliy.java.amaliy1.entity.Client;
import amaliy.java.amaliy1.entity.EmployeeEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClientStatisticsService {

    public ClientStatisticsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    private final ClientRepository clientRepository;

    public long countClientsToday() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        return clientRepository.countByCreatedAtBetween(start,end);
    }
    public EmployeeEntity topCustomerServiceEmployee() {
        return clientRepository.findAll().stream()
                .filter(c -> c.getCreatedBy() != null)
                .filter(c -> "CLIENTS".equalsIgnoreCase(
                        c.getCreatedBy().getDepartment().getName()))
                .collect(Collectors.groupingBy(Client::getCreatedBy, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }


    public long countClientsLastMonth(){
        LocalDate firstDayOfCurrentMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate firstDayOfLastMonth = LocalDate.now().withDayOfMonth(1);
        return clientRepository.findAll().stream()
                .map(c -> c.getCreatedAt().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .filter(date -> !date.isBefore(firstDayOfCurrentMonth) && date.isBefore(firstDayOfLastMonth))
                .count();
    }
    public List<EmployeeEntity> top3Employees() {
        return clientRepository.findAll().stream()
                .filter(c->c.getCreatedBy()!=null)
                .collect(Collectors.groupingBy(c->c.getCreatedBy(),Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<EmployeeEntity,Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }
    public long countLastMonth() {
        LocalDateTime start = LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        return clientRepository.findAll().stream()
                .filter(c -> {
                    LocalDateTime created = c.getCreatedAt().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    return !created.isBefore(start) && created.isBefore(end);
                })
                .count();
    }
    public LocalDate busiestDayLastMonth() {
        LocalDateTime start = LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().withDayOfMonth(1).atStartOfDay();

        return clientRepository.findAll().stream()
                .filter(c -> {
                    LocalDateTime created = c.getCreatedAt().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    return !created.isBefore(start) && created.isBefore(end);
                })

                .collect(Collectors.groupingBy(c -> c.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

}
