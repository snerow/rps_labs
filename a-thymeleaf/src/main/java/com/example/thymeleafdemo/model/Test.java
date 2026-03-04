package com.example.thymeleafdemo.model;

public class Test {

    private Long id;
    private String name;
    private String subject;
    private String description;

    public Test() {
    }

    public Test(Long id, String name, String subject, String description) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
