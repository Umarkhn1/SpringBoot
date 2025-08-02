package amaliy.java.amaliy1.service;

import amaliy.java.amaliy1.Repository.ClientRepository;
import amaliy.java.amaliy1.Repository.EmployeeRepository;
import amaliy.java.amaliy1.entity.Client;
import amaliy.java.amaliy1.entity.EmployeeEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    public ClientService(ClientRepository clientRepository, EmployeeRepository employeeRepository) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
    }
    public Client save(Client client) {

        return clientRepository.save(client);
    }
    public List<Client> findAllByCreatedLogin(String login) {

        return clientRepository.findAllByCreatedBy_Login(login);
    }
    public Client findById(Long id) {

        return clientRepository.findById(id).orElse(null);
    }
    public void deleteById(Long id) {

        clientRepository.deleteById(id);
    }


}
