package com.example.thymeleafdemo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "institutes")
public class Institute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cyrillicName;

    @OneToMany(mappedBy = "institute", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Department> departments = new ArrayList<>();

    public Institute() {}

    public Institute(String name, String cyrillicName) {
        this.name = name;
        this.cyrillicName = cyrillicName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCyrillicName() { return cyrillicName; }
    public void setCyrillicName(String cyrillicName) { this.cyrillicName = cyrillicName; }

    public List<Department> getDepartments() { return departments; }
    public void setDepartments(List<Department> departments) { this.departments = departments; }

    public void addDepartment(Department department) {
        departments.add(department);
        department.setInstitute(this);
    }
}
