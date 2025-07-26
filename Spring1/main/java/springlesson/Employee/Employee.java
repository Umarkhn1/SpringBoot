package springlesson.Employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import springlesson.FileStorage.Item;

import java.io.Serializable;
import java.util.Set;

@Entity

public class Employee implements Serializable {
    @Id
    private Long id;
    private String name;
    private String surname;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Email",length = 50)
    @Email
    private String email;

    @ManyToOne
    private Department department;

    @OneToOne(optional = false)
    @JoinColumn(name = "account_id",unique = true, nullable = false)
    private Account account;


    @ManyToMany
    @JoinTable(
            name = "dev_employee_project" ,
            joinColumns = {@JoinColumn(name = "employee_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id",referencedColumnName = "id")}
    )
    private Set <Project> project;

    public Set<Project> getProject() {
        return project;
    }

    public void setProject(Set<Project> project) {
        this.project = project;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @OneToMany
    private Set<Item> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
