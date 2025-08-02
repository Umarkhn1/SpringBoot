package amaliy.java.amaliy1.Repository;

import amaliy.java.amaliy1.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {

    EmployeeEntity findByLogin(String login);
    @Query("SELECT SUM (e.salary) FROM EmployeeEntity e")
    Double sumSalary();

    @Query("SELECT COUNT (e) FROM EmployeeEntity e")
    long total();

    @Query("SELECT e.department.name, COUNT(e) FROM EmployeeEntity e GROUP BY e.department.name")
    List<Object[]> departmentEmployees();

    List<EmployeeEntity> findByAgeBetween(int max, int min);

}
