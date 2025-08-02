package springJWT.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springJWT.demo.Repository.UserRepository;
import springJWT.demo.domain.Authority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lowerCaseUsername = username.toLowerCase();

        return userRepository.findByUsername(lowerCaseUsername)
                .map(user->createSpringSecurityUser(lowerCaseUsername,user))
                .orElseThrow(() -> new UsernameNotFoundException("User"+username+ "was not found"));
    }
    private User createSpringSecurityUser(String username, springJWT.demo.domain.User user) {
        if(!user.isActivated()){
            throw new UserNotActivatedException("User"+username+" is not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream().map(Authority::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    return new User(username,user.getPassword(),grantedAuthorities);
    }

}
