package com.example.thymeleafdemo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cyrillicName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", nullable = false)
    private Institute institute;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Specialist> specialists = new ArrayList<>();

    public Department() {}

    public Department(String name, String cyrillicName) {
        this.name = name;
        this.cyrillicName = cyrillicName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCyrillicName() { return cyrillicName; }
    public void setCyrillicName(String cyrillicName) { this.cyrillicName = cyrillicName; }

    public Institute getInstitute() { return institute; }
    public void setInstitute(Institute institute) { this.institute = institute; }

    public List<Specialist> getSpecialists() { return specialists; }
    public void setSpecialists(List<Specialist> specialists) { this.specialists = specialists; }

    public void addSpecialist(Specialist specialist) {
        specialists.add(specialist);
        specialist.setDepartment(this);
    }
}
