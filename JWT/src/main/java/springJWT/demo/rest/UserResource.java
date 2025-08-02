package springJWT.demo.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springJWT.demo.service.UserService;
import springJWT.demo.domain.User;

@RestController
@RequestMapping("/api")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity create(@RequestBody User user) {
        User result =userService.save(user);
          return ResponseEntity.ok(result);
    }
    @GetMapping("/users")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

}
