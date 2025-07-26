package springlesson.Employee;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAll();

//    @Query("SELECT e FROM Employee e WHERE e.name = :name and e.surname =:surname")
//    List<Employee> findAll(@Param("name")String name
//    , @Param("surname")String surname);


    List<Employee> findAllByNameAndSurname(String name,String surname);
    List<Employee> findAllByNameStartingWithOrderByIdAsc(String name);
    List<Employee> findAllByNameEndingWith(String name);

//    @Query(value = "SELECT * from Employee e WHERE e.name= ",nativeQuery = true)
//    List<Employee> findAll(@Param ("name") String name);
    @Query("SELECT e from Employee e WHERE upper (e.name) like upper(concat(:name,'%')) order by e.id asc")
    List<Employee> findAllByNameStartingWithJPQL(String name);

    @Query("SELECT e from Employee e WHERE upper (e.name) like upper(concat('%',:name)) ")
    List<Employee> findAllByNameEndingWithgJPQL(String name);

    @Query("SELECT e from Employee e WHERE upper (e.name) like upper(concat('%',:name,'%')) ")
    List<Employee> findAllByName(String name);


    List<Employee> findByNameLike(@Param("name") String name);

    @Query(value = "SELECT * from Employee e WHERE e.name like =:name" ,nativeQuery = true)
     List<Employee> findByNameLiked(@Param("name") String name);
}

