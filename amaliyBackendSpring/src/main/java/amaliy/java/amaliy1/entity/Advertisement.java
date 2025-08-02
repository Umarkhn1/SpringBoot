package amaliy.java.amaliy1.entity;

import amaliy.java.amaliy1.Repository.AdvertisementRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Advertisement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String platform;
    private double cost;
    private Date startedDateTimedAt;
    private Integer duration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({
            "lastName", "middleName", "age", "salary", "address",
            "department", "passport", "clients", "login", "password"
    })
    private EmployeeEntity createdBy;




    public EmployeeEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(EmployeeEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @PrePersist
    public void setCreatedAt() {
        this.startedDateTimedAt = new Date();
    }


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getStartedDateTimedAt() {
        return startedDateTimedAt;
    }
    public Date getEndDateTimedAt() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startedDateTimedAt);
        cal.add(Calendar.DATE, duration);
        return cal.getTime();
    }

}
