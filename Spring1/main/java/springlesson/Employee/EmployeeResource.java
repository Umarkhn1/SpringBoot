package springlesson.Employee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final EmployeeService employeeService;


    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping("/employee")
    public ResponseEntity create(@RequestBody Employee employee){
        Employee result =  employeeService.save(employee);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/employee")
    public ResponseEntity update(@RequestBody Employee employee){
        if(employee.getId() == null){
            return ResponseEntity.badRequest().build();
        }
        Employee result =  employeeService.save(employee);
    return ResponseEntity.ok(result);
    }
    @GetMapping("/employee/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        Employee result = employeeService.findById(id);
        return ResponseEntity.ok(result);
    }


//    @GetMapping("/employee/{id}")
//    public ResponseEntity findById2(@PathVariable Long id){
//        Employee result =  employeeService.findById2(id);
//        return ResponseEntity.ok(result);
//    }
    @GetMapping("/employee")
    public ResponseEntity findAll(){
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }
    @GetMapping("/employee/all")
    public ResponseEntity findByFirstNameLike(@RequestParam String name, @RequestParam String surname ){
        List<Employee> employees = employeeService.findAllByNameAndSurname(name,surname);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employee/like")
    public ResponseEntity findByNameLike(@RequestParam String name){
        List<Employee> employees = employeeService.findByFirstnameLike(name);
        return ResponseEntity.ok(employees);
    }
    @GetMapping("/employee/like1")
    public ResponseEntity findByNameLiked(@RequestParam String name){
        List<Employee> employees = employeeService.findByNameLiked(name);
        return ResponseEntity.ok(employees);
    }


    @DeleteMapping("/employee/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        employeeService.delete(id);
        return ResponseEntity.ok("Deleted");
    }


}

