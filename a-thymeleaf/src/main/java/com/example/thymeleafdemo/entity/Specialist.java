package com.example.thymeleafdemo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "specialists")
public class Specialist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cyrillicName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Group> groups = new ArrayList<>();

    public Specialist() {}

    public Specialist(String name, String cyrillicName) {
        this.name = name;
        this.cyrillicName = cyrillicName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCyrillicName() { return cyrillicName; }
    public void setCyrillicName(String cyrillicName) { this.cyrillicName = cyrillicName; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public List<Group> getGroups() { return groups; }
    public void setGroups(List<Group> groups) { this.groups = groups; }

    public void addGroup(Group group) {
        groups.add(group);
        group.setSpecialist(this);
    }
}
