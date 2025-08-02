package amaliy.java.amaliy1.contoller;

import amaliy.java.amaliy1.Repository.ClientRepository;
import amaliy.java.amaliy1.Repository.EmployeeRepository;
import amaliy.java.amaliy1.entity.Advertisement;
import amaliy.java.amaliy1.entity.Client;
import amaliy.java.amaliy1.entity.EmployeeEntity;
import amaliy.java.amaliy1.model.DepartmentStats;
import amaliy.java.amaliy1.service.ClientService;
import amaliy.java.amaliy1.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/director")
public class EmployeeResource {
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    public EmployeeResource(ClientService clientService, EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody EmployeeEntity employeeEntity) {
        EmployeeEntity result= employeeService.save(employeeEntity);
    return ResponseEntity.ok(result);
    }


    @PutMapping("/edit")
    public ResponseEntity edit(@RequestBody EmployeeEntity employeeEntity) {
        if(employeeEntity.getId() == null){
            return ResponseEntity.badRequest().build();
        }
        EmployeeEntity result = employeeService.save(employeeEntity);
    return ResponseEntity.ok(result);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @GetMapping("/pages")
    @Transactional(readOnly = true)
    public Page<EmployeeEntity> findAll(@RequestParam int page, @RequestParam int size) {
        return employeeRepository.findAll(PageRequest.of(page,size));

    }
    @GetMapping("/departments")
    public List<DepartmentStats> departments() {
           return employeeService.departmentStats();
    }

    @GetMapping("/totalsalary")
    public ResponseEntity totalSalary() {
        Double response = employeeService.getSumSalary();
    return ResponseEntity.ok(response);
    }
    @GetMapping("/ages")
    public ResponseEntity ages(@RequestParam int min, @RequestParam int max) {
        List<EmployeeEntity> result =  employeeRepository.findByAgeBetween(min,max);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }


}
