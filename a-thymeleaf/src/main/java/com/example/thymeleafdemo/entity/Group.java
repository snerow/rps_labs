package com.example.thymeleafdemo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cyrillicName;

    @Column(name = "group_year", nullable = false)
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_id", nullable = false)
    private Specialist specialist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_type_id", nullable = false)
    private GroupType groupType;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudentJpa> students = new ArrayList<>();

    public Group() {}

    public Group(String name, String cyrillicName, Integer year) {
        this.name = name;
        this.cyrillicName = cyrillicName;
        this.year = year;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCyrillicName() { return cyrillicName; }
    public void setCyrillicName(String cyrillicName) { this.cyrillicName = cyrillicName; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Specialist getSpecialist() { return specialist; }
    public void setSpecialist(Specialist specialist) { this.specialist = specialist; }

    public GroupType getGroupType() { return groupType; }
    public void setGroupType(GroupType groupType) { this.groupType = groupType; }

    public List<StudentJpa> getStudents() { return students; }
    public void setStudents(List<StudentJpa> students) { this.students = students; }

    public void addStudent(StudentJpa student) {
        students.add(student);
        student.setGroup(this);
    }
}
