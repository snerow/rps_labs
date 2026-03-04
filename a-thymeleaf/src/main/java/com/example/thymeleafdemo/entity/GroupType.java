package com.example.thymeleafdemo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group_types")
public class GroupType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String cyrillicTitle;

    @OneToMany(mappedBy = "groupType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Group> groups = new ArrayList<>();

    public GroupType() {}

    public GroupType(String title, String cyrillicTitle) {
        this.title = title;
        this.cyrillicTitle = cyrillicTitle;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCyrillicTitle() { return cyrillicTitle; }
    public void setCyrillicTitle(String cyrillicTitle) { this.cyrillicTitle = cyrillicTitle; }

    public List<Group> getGroups() { return groups; }
    public void setGroups(List<Group> groups) { this.groups = groups; }

    public void addGroup(Group group) {
        groups.add(group);
        group.setGroupType(this);
    }
}
