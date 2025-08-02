package amaliy.java.amaliy1.SecurityConfiguraton;

import amaliy.java.amaliy1.Repository.EmployeeRepository;
import amaliy.java.amaliy1.entity.EmployeeEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class EmployeeDetails implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    public EmployeeDetails(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmployeeEntity employeeEntity = employeeRepository.findByLogin(username);
        if (employeeEntity == null) {
            throw new UsernameNotFoundException("Сотрудник не найден: " + username);
        }

        String department = employeeEntity.getDepartment().getName().toUpperCase();
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + department)
        );

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorities;
            }

            @Override
            public String getPassword() {
                return employeeEntity.getPassword();
            }

            @Override
            public String getUsername() {
                return employeeEntity.getLogin();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
