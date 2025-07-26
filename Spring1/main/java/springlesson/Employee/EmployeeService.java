package springlesson.Employee;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {

        return employeeRepository.save(employee);
    }

    public void delete(Long id) {

        employeeRepository.deleteById(id);
    }
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
    public Employee findById2(Long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
//    public List <Employee> findAll(String name, String surname) {
//
//        return employeeRepository.findAll(name,surname);
//    }
    public List<Employee> findAllByNameAndSurname(String name,String surname) {
        return employeeRepository.findAllByNameAndSurname(name,surname);
    }
    public List<Employee> findByFirstnameLike(String name) {
        return employeeRepository.findByNameLike("%"+name+"%");
    }
    public List<Employee> findByNameLiked(String name) {
        return employeeRepository.findAllByNameStartingWithJPQL(name);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
