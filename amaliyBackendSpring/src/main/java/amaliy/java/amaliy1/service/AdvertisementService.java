package amaliy.java.amaliy1.service;

import amaliy.java.amaliy1.Repository.AdvertisementRepository;
import amaliy.java.amaliy1.entity.Advertisement;
import amaliy.java.amaliy1.entity.EmployeeEntity;
import amaliy.java.amaliy1.model.EmployeeSpending;
import jakarta.servlet.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public Advertisement save(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    public Advertisement findById(Long id) {
        return advertisementRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        advertisementRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Advertisement> findAll(Pageable pageable) {
        return advertisementRepository.findAll(pageable);
    }


    public Map.Entry<String,Double> mostExpensiveType() {
        return advertisementRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Advertisement::getPlatform, Collectors.summingDouble(Advertisement::getCost)

                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

    }

    public EmployeeSpending mostSpentBy() {
        return advertisementRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Advertisement::getCreatedBy, Collectors.summingDouble(Advertisement::getCost)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> {
                    EmployeeEntity employee = entry.getKey();
                    String fullName = employee.getFirstName() + " " + employee.getLastName();
                    return new EmployeeSpending(fullName, entry.getValue());
                })
                .orElse(null);
    }

    public long addedLastMonth() {
        LocalDateTime start = LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().withDayOfMonth(1).atStartOfDay();

        return advertisementRepository.findAll().stream()

                .map(ad -> {
                    LocalDateTime started = ad.getStartedDateTimedAt().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    return started.plusDays(ad.getDuration());
                })
                .filter(endDate -> !endDate.isBefore(start) && endDate.isBefore(end))
                .count();
    }

    public long endedLastMonth() {
        LocalDateTime start = LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        return advertisementRepository.findAll().stream()
                .filter(ad -> {
                    LocalDateTime endDateTime = ad.getEndDateTimedAt().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    return !endDateTime.isBefore(start) &&
                            endDateTime.isBefore(end);
                })
                .count();
    }
    public Map<String,Double> totalCostOfTypes(){
       return advertisementRepository.findAll().stream()
                .collect(Collectors.groupingBy(Advertisement::getPlatform,
                        Collectors.summingDouble(Advertisement::getCost)));
    }


}
