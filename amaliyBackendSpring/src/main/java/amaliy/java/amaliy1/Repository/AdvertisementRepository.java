package amaliy.java.amaliy1.Repository;

import amaliy.java.amaliy1.entity.Advertisement;
import amaliy.java.amaliy1.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
