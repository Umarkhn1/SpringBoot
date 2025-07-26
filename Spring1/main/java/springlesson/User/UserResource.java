package springlesson.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    ResponseEntity create(@RequestBody User user){
        if(userService.existUser(user.getLogin())){
            return new ResponseEntity("Login already exists", HttpStatus.BAD_REQUEST);
        }
        if(checkPassword(user.getPassword())){
            return new ResponseEntity("Too short password", HttpStatus.BAD_REQUEST);
        }
        User result = userService.save(user);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/alter")
    public ResponseEntity update(@RequestBody User user){
       User response = userService.save(user);
       return ResponseEntity.ok(response);

    }


    private boolean checkPassword(String password){
        return password.length() <= 4;
    }


}
