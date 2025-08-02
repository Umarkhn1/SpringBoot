package amaliy.java.amaliy1.contoller;

import amaliy.java.amaliy1.audit.Auditable;
import amaliy.java.amaliy1.service.ClientStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/director/client_stats")
public class ClientStatisticController {
    private final ClientStatisticsService clientStatisticsService;

    public ClientStatisticController(ClientStatisticsService clientStatisticsService) {
        this.clientStatisticsService = clientStatisticsService;
    }
    @Auditable(action = "Клиенты за сегодня")
    @GetMapping("/today")
    public ResponseEntity today() {
        Long response = clientStatisticsService.countClientsToday();
        return ResponseEntity.ok(response);
    }
    @Auditable(action = "Топ1 работник")
    @GetMapping("/top1")
    public ResponseEntity top1() {
        return ResponseEntity.ok(clientStatisticsService.topCustomerServiceEmployee());
    }
    @Auditable(action = "Топ3 работников")
    @GetMapping("/top3")
    public ResponseEntity top3() {
        return ResponseEntity.ok(clientStatisticsService.top3Employees());
    }
    @Auditable(action = "В прошлом месяце в общем")
    @GetMapping("/lastmonth/total")
    public ResponseEntity lastmonth() {
        return ResponseEntity.ok(clientStatisticsService.countClientsLastMonth());
    }
    @Auditable(action = "В прошлом месяце занятой день")
    @GetMapping("/lastmonth/topday")
    public ResponseEntity lastmonthtopday() {
        return ResponseEntity.ok(clientStatisticsService.busiestDayLastMonth());
    }
}
