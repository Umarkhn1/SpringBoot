package springJWT.demo.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springJWT.demo.Repository.UserRepository;
import springJWT.demo.domain.User;
import springJWT.demo.rest.vm.LoginVm;
import springJWT.demo.security.TokenProvider;

@RestController
@RequestMapping("/api")
public class UserController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    public UserController( AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authentication")
    public ResponseEntity<JWTToken> authorize(@RequestBody LoginVm loginVm) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginVm.getUsername(), loginVm.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication,loginVm.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt),httpHeaders, HttpStatus.OK);
    }
    static class JWTToken{
        private String token;

        public JWTToken(String token) {
            this.token = token;
        }
        @JsonProperty("jwt_token")
        public String getToken() {
            return token;
        }
    }
}
//    @PostMapping("/create")
//    public ResponseEntity create(@RequestBody User user) {
//        User response = userRepository.save(user);
//        return ResponseEntity.ok(response);
//    }

//
//    @PostMapping("/authenticate")
//    public ResponseEntity<JWTToken> authenticate(@RequestBody LoginVm loginVm) {
//        UsernamePasswordAuthenticationToken  authenticationToken = new UsernamePasswordAuthenticationToken
//                (loginVm.getUsername(), loginVm.getPassword());
//        Authentication authentication  = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }

