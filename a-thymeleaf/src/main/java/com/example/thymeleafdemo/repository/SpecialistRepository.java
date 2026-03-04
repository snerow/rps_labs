package com.example.thymeleafdemo.repository;

import com.example.thymeleafdemo.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Long> {

    List<Specialist> findByNameContainingIgnoreCase(String name);

    List<Specialist> findByDepartmentId(Long departmentId);

    @Query("SELECT s FROM Specialist s WHERE s.department.institute.id = :instituteId")
    List<Specialist> findByInstituteId(@Param("instituteId") Long instituteId);

    @Query("SELECT COUNT(s) FROM Specialist s WHERE s.department.id = :departmentId")
    Long countByDepartmentId(@Param("departmentId") Long departmentId);
}
