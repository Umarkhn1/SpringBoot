package oauth2.Java.webrest.Service;

import oauth2.Java.Entity.User;
import oauth2.Java.Repository.UserRepository;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserFromAuth(AbstractAuthenticationToken auth) {
        Map<String, Object> attributes;
        if(auth instanceof OAuth2AuthenticationToken){
            attributes = ((OAuth2AuthenticationToken) auth).getPrincipal().getAttributes();
        }
        else {
            throw new IllegalArgumentException("Invalid authentication token");
        }
        User user = getUser(attributes);
        if(user != null){
            return userRepository.save(user);
        }

        return user;
    }


    private User getUser(Map<String, Object> attributes) {
        User user = new User();
        if(attributes.get("uid")!=null){
            user.setUid((String) attributes.get("uid"));
        }
        if(attributes.get("given_name")!=null){
            user.setFirstName((String) attributes.get("given_name"));
        }
        if(attributes.get("familyName")!=null){
            user.setLastName((String) attributes.get("familyName"));
        }
        if(attributes.get("locale")!=null){
            user.setLangKey((String) attributes.get("locale"));
        }
        if(attributes.get("email_verified")!=null){
            user.setActivated((Boolean) attributes.get("email_verified"));
        }
        if(attributes.get("email")!=null){
            user.setEmail((String) attributes.get("email"));
        }
        if(attributes.get("picture")!=null){
            user.setImageUrl((String) attributes.get("picture"));
        }
        return user;
    }

}
