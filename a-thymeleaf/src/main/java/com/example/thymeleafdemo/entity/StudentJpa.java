package com.example.thymeleafdemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students_jpa")
public class StudentJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    public StudentJpa() {}

    public StudentJpa(String userId) {
        this.userId = userId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
}
