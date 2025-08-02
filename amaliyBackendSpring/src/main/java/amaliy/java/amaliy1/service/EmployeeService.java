package amaliy.java.amaliy1.service;

import amaliy.java.amaliy1.Repository.EmployeeRepository;
import amaliy.java.amaliy1.entity.Advertisement;
import amaliy.java.amaliy1.entity.Client;
import amaliy.java.amaliy1.entity.EmployeeEntity;
import amaliy.java.amaliy1.model.DepartmentStats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public EmployeeEntity save(EmployeeEntity employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }

    public EmployeeEntity findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
    public Double getSumSalary() {
        return employeeRepository.sumSalary();
    }
    public List<EmployeeEntity> findByAge(int min, int max) {
        return employeeRepository.findByAgeBetween(min, max);
    }
    @Transactional(readOnly = true)
    public Page<EmployeeEntity> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public List<DepartmentStats> departmentStats(){
        long total = employeeRepository.total();
        List<Object[]> rows = employeeRepository.departmentEmployees();
        return rows.stream()
                .map(row -> new DepartmentStats(
                        (String) row[0],
                        (Long) row[1],
                        Math.round((double)((Long) row[1]) * 100 / total * 10.0) / 10.0
                ))
                .toList();
    }

    }


