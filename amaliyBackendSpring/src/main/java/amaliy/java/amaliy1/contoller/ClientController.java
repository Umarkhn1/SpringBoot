package amaliy.java.amaliy1.contoller;

import amaliy.java.amaliy1.Repository.EmployeeRepository;
import amaliy.java.amaliy1.audit.Auditable;
import amaliy.java.amaliy1.entity.Client;
import amaliy.java.amaliy1.entity.EmployeeEntity;
import amaliy.java.amaliy1.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {
    private final ClientService clientService;
    private final EmployeeRepository employeeRepository;
    public ClientController(ClientService clientService, EmployeeRepository employeeRepository) {
        this.clientService = clientService;
        this.employeeRepository = employeeRepository;
    }
    @Auditable(action = "Создание клиента")
    @PostMapping("/client/add")
    public ResponseEntity addClient(@RequestBody Client client) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        EmployeeEntity currentEmployee = employeeRepository.findByLogin(login);
        client.setCreatedBy(currentEmployee);
        Client savedClient = clientService.save(client);
        return ResponseEntity.ok(savedClient);
    }
    @Auditable(action = "Архивирование")
    @PutMapping("/client/archive/{id}")
    public ResponseEntity archive(@PathVariable Long id) {
        Client client = clientService.findById(id);
        client.setArchived(true);
        clientService.save(client);
        return ResponseEntity.ok("archived");
    }
    @Auditable(action = "Изменение клиента")
    @PutMapping("/client/edit")
    public ResponseEntity edit(@RequestBody Client client) {
        if(client.getId() == null){
            return ResponseEntity.badRequest().build();
        }
        Client result = clientService.save(client);
        return ResponseEntity.ok(result);
    }
    @Auditable(action = "Инфо о клиенте")
    @GetMapping("/client/get/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }
    @Auditable(action = "Мои клиенты")
    @GetMapping("/client/my")
    public ResponseEntity getMyClients() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();

        List<Client> myClients = clientService.findAllByCreatedLogin(login);

        return ResponseEntity.ok(myClients);
    }
    @Auditable(action = "Удаление клиента")
    @DeleteMapping("/delete/client/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }
}
