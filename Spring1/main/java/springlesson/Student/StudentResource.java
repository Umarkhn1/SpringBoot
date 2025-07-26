package springlesson.Student;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping("/api")
public class StudentResource {
//    @GetMapping("/students")
//    public ResponseEntity hello(){
//    return ResponseEntity.ok("Hello World");
//
//}
//    @RequestMapping(value = "/students", method = RequestMethod.GET)
//
//    public ResponseEntity hello() {
//        return ResponseEntity.ok("Hello World");
//
//    }
    @PostMapping("/students")
    public ResponseEntity create(@RequestBody StudentModel studentModel){
        return ResponseEntity.ok(studentModel);
    }

    @PostMapping("/students/list")
    public ResponseEntity createall(@RequestBody List<StudentModel> studentModels){
        return ResponseEntity.ok(studentModels);

    }


    @PutMapping("/students")
    public ResponseEntity update(@RequestBody StudentModel studentModel) {
        studentModel.setLastname("Utkirxojayev");
        return ResponseEntity.ok(studentModel);
    }
    @PutMapping("/students/{id}")
    public ResponseEntity update2(@PathVariable Long id ,@RequestBody StudentModel studentModel) {
        studentModel.setLastname("Utkirxojayev");
        studentModel.setId(id);
        return ResponseEntity.ok(studentModel);
    }
    @GetMapping("/students/{id}")
        public ResponseEntity <StudentModel> getOne(@PathVariable Long id){
            StudentModel studentModel = new StudentModel();
            studentModel.setId(id);
            return ResponseEntity.ok(studentModel);
        }
        @GetMapping("/students")
    public ResponseEntity getAll(@RequestParam Long id,
                                 @RequestParam String name,
                                 @RequestParam String lastname,
                                 @RequestParam Integer age){
        List<StudentModel> studentModels = new ArrayList<>();
            Course  course = new Course();
            course.setId(2L);
            course.setName("Umarxon");
     studentModels.add(new StudentModel(id,name,lastname,age,course));
     studentModels.add(new StudentModel(1L,"name","lastname",34,course));

     return ResponseEntity.ok(studentModels);

        }
        @DeleteMapping("/students/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return ResponseEntity.ok(id+" : StudentModel deleted successfully");
        }
       @PatchMapping("/students/{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody StudentModel studentModel){
        return ResponseEntity.ok(studentModel);
       }


}