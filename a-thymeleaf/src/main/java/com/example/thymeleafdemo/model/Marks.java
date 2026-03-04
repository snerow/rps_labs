package com.example.thymeleafdemo.model;

public class Marks {

    private Long studentId;
    private int math;
    private int physics;
    private int chemistry;

    public Marks() {
        this.math = 0;
        this.physics = 0;
        this.chemistry = 0;
    }

    public Marks(Long studentId, int math, int physics, int chemistry) {
        this.studentId = studentId;
        this.math = math;
        this.physics = physics;
        this.chemistry = chemistry;
    }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public int getMath() { return math; }
    public void setMath(int math) { this.math = math; }

    public int getPhysics() { return physics; }
    public void setPhysics(int physics) { this.physics = physics; }

    public int getChemistry() { return chemistry; }
    public void setChemistry(int chemistry) { this.chemistry = chemistry; }
}
