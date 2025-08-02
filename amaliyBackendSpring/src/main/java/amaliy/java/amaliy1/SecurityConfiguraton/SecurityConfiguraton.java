package amaliy.java.amaliy1.SecurityConfiguraton;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguraton {
    private final UserDetailsService userDetailsService;

    public SecurityConfiguraton(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http
            .userDetailsService(userDetailsService)
            .csrf(csrf ->csrf.disable() )
            .headers(headers ->headers.disable())
            .authorizeHttpRequests(auth->auth
                    .requestMatchers("/api/client/**").hasRole("CLIENTS")
                    .requestMatchers("/api/director/**").hasRole("PERSONAL")
                    .requestMatchers("/api/advertisement/**").hasRole("SALES")
                    .anyRequest().authenticated()

            )
            .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout.permitAll());



        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

