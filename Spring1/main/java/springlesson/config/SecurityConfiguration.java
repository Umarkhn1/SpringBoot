package springlesson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springlesson.Security.JWTConfigure;
import springlesson.Security.JWTFilter;
import springlesson.Security.JwtProvider;
import springlesson.User.UserRepository;
import springlesson.User.SpringSecurity.CustomerUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
private final JwtProvider jwtProvider;
private final JWTFilter jwtFilter;

    public SecurityConfiguration(@Lazy JwtProvider jwtProvider,@Lazy JWTFilter jwtFilter) {
        this.jwtProvider = jwtProvider;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .userDetailsService(userDetailsService())
                .csrf(csrf -> csrf.disable())                     // заменяет .csrf().disable().and()
                .headers(headers -> headers.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/posts/pages/**").hasRole("ADMIN")
                        .requestMatchers("/api/posts").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/api/authenticate", "/api/register").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(customizer -> customizer.disable())
                .logout(logout -> logout.permitAll());
                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }

    @Bean
    public JWTFilter jwtFilter(JwtProvider jwtProvider, UserDetailsService userDetailsService) {
        return new JWTFilter(jwtProvider, userDetailsService);
    }
//        var userDetailsManager = new InMemoryUserDetailsManager();
//
//        userDetailsManager.createUser(
//                User.withUsername("admin")
//                        .password(passwordEncoder().encode("1234"))
//                        .roles("ADMIN")
//                        .build()
//        );
//        userDetailsManager.createUser(
//                User.withUsername("user")
//                        .password(passwordEncoder().encode("1234"))
//                        .roles("USER")
//                        .build()
//        );

    }

