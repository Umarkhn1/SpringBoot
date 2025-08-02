package springJWT.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {
    private final Key key;
    private final JwtParser jwtParser;
    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private long tokenVallidateMillieSecondRemember;
    private long tokenVallidateMillieSecond;

    public TokenProvider() {
        byte[] keyByte;
        String secret = "zB2r+XxT0c8vZf5O/IG0Z6lb6Z6oy3PrmxK1FVbhyOE=";
        keyByte = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyByte);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenVallidateMillieSecondRemember = 1000*86400;
        this.tokenVallidateMillieSecond = 1000*3600;
    }


    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        long now = (new Date().getTime());
        Date validate;
        if (rememberMe) {
            validate = new Date(now+tokenVallidateMillieSecondRemember);
        }
        else {
            validate = new Date(now+tokenVallidateMillieSecond);
        }
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY,authorities)
                .signWith(key,SignatureAlgorithm.HS256)
                .setExpiration(validate)
                .compact();
    }

    public Authentication getAuthentication(String jwt) {
        Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User principal = new User(claims.getSubject(),"",authorities);
        return new UsernamePasswordAuthenticationToken(principal,"",authorities);

    }
    public boolean validateToken(String jwt) {
        try {
            jwtParser.parseClaimsJws(jwt);
            return true;
        }
        catch (ExpiredJwtException e){
            log.error("ExpiredJwtException");
        }
        catch (UnsupportedJwtException u){
            log.error("UnsupportedJwtException");
        }
        catch (MalformedJwtException m){
            log.error("MalformedJwtException");
        }
        catch (SignatureException s){
            log.error("SignatureException");
        }
        catch (IllegalArgumentException i){
            log.error("IllegalArgumentException");
        }
        return false;
    }


}
