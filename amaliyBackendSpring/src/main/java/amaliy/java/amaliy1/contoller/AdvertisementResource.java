package amaliy.java.amaliy1.contoller;

import amaliy.java.amaliy1.Repository.EmployeeRepository;
import amaliy.java.amaliy1.audit.Auditable;
import amaliy.java.amaliy1.entity.Advertisement;
import amaliy.java.amaliy1.entity.EmployeeEntity;
import amaliy.java.amaliy1.service.AdvertisementService;
import amaliy.java.amaliy1.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AdvertisementResource {
private final AdvertisementService advertisementService;

    public AdvertisementResource(AdvertisementService advertisementService, EmployeeRepository employeeRepository) {
        this.advertisementService = advertisementService;
        this.employeeRepository = employeeRepository;
    }
    public final EmployeeRepository employeeRepository;

    @Auditable(action = "Создание рекламы")
    @PostMapping("/advertisement/add")
    public ResponseEntity createAdvertisement(@RequestBody Advertisement advertisement) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        EmployeeEntity currentEmployee = employeeRepository.findByLogin(username);
        advertisement.setCreatedBy(currentEmployee);
        Advertisement response = advertisementService.save(advertisement);
        return ResponseEntity.ok(response);
    }
    @Auditable(action = "Изменение рекламы")
    @PutMapping("/advertisement/edit")
    public ResponseEntity updateAdvertisement(@RequestBody Advertisement advertisement) {
        if (advertisement.getId() == null) {
            return ResponseEntity.badRequest().body("id is required");
        }
        Advertisement result = advertisementService.save(advertisement);
        return ResponseEntity.ok(result);
    }
    @Auditable(action = "Инфо о рекламе")
    @GetMapping("/advertisement/get/{id}")
    public ResponseEntity getAdvertisementById(@PathVariable("id") Long id) {
        Advertisement advertisement = advertisementService.findById(id);
        return  ResponseEntity.ok(advertisement);
    }

    @Auditable(action = "Странницы")
    @GetMapping("/advertisement/pages")
    @Transactional(readOnly = true)
    public Page<Advertisement> findAll(@RequestParam int page, @RequestParam int size) {
        return advertisementService.findAll(PageRequest.of(page,size));

    }
    @Auditable(action = "Удаление рекламы")
    @DeleteMapping("/advertisement/delete/{id}")
    public void delete(@PathVariable Long id){
        advertisementService.deleteById(id);
    }
}
