package com.example.thymeleafdemo.repository;

import com.example.thymeleafdemo.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    // Spring Data JPA derived query
    List<Group> findByNameContainingIgnoreCase(String name);

    List<Group> findByYear(Integer year);

    List<Group> findBySpecialistId(Long specialistId);

    List<Group> findByGroupTypeId(Long groupTypeId);

    // Custom JPQL query
    @Query("SELECT g FROM Group g WHERE g.year >= :minYear AND g.year <= :maxYear")
    List<Group> findByYearRange(@Param("minYear") Integer minYear, @Param("maxYear") Integer maxYear);

    // Custom native SQL query
    @Query(value = "SELECT * FROM student_groups WHERE group_year = :year ORDER BY name", nativeQuery = true)
    List<Group> findByYearOrdered(@Param("year") Integer year);

    // Count groups by specialist
    @Query("SELECT COUNT(g) FROM Group g WHERE g.specialist.id = :specialistId")
    Long countBySpecialistId(@Param("specialistId") Long specialistId);
}
