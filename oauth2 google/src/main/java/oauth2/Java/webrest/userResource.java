package oauth2.Java.webrest;

import oauth2.Java.webrest.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class userResource {
    private final UserService userService;

    public userResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
    @GetMapping("/user")
    public ResponseEntity getUser(Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            return ResponseEntity.ok(userService.getUserFromAuth((AbstractAuthenticationToken) principal));
        } else {
            throw new IllegalArgumentException("Invalid authentication token");
        }
    }

}
