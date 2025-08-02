package amaliy.java.amaliy1.contoller;

import amaliy.java.amaliy1.audit.Auditable;
import amaliy.java.amaliy1.entity.Advertisement;
import amaliy.java.amaliy1.service.AdvertisementService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api/advertisement")
public class AdvertisementStatistics {
    private final AdvertisementService advertisementService;

    public AdvertisementStatistics(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }
    @Auditable(action = "Дорогие виды")
    @GetMapping("/expensive_type")
    public ResponseEntity expensiveType() {
        return ResponseEntity.ok(advertisementService.mostExpensiveType());
    }

    @Auditable(action = "Много потрачено кем")
    @GetMapping("/mostspentby")
    public ResponseEntity mostSpentBy() {
        return ResponseEntity.ok(advertisementService.mostSpentBy());
    }

    @Auditable(action = "Прошлом месяце добавленные")
    @GetMapping("/lastmonth_added")
    public ResponseEntity lastMonthAdded() {
        return ResponseEntity.ok(advertisementService.addedLastMonth());
    }

    @Auditable(action = "Прошлом месяце завершенные")
    @GetMapping("/lastmonth_ended")
    public ResponseEntity lastMonthEnded() {
        return ResponseEntity.ok(advertisementService.endedLastMonth());
    }
    @Auditable(action = "Общая сумма")
    @GetMapping("/total_cost")
    public ResponseEntity totalCost() {
        return ResponseEntity.ok(advertisementService.totalCostOfTypes());
    }
}
