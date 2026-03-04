package com.example.thymeleafdemo.repository;

import com.example.thymeleafdemo.entity.StudentJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentJpaRepository extends JpaRepository<StudentJpa, Long> {

    // Spring Data JPA derived query
    Optional<StudentJpa> findByUserId(String userId);

    List<StudentJpa> findByGroupId(Long groupId);

    boolean existsByUserId(String userId);

    // Custom JPQL query
    @Query("SELECT s FROM StudentJpa s WHERE s.group.id = :groupId ORDER BY s.userId")
    List<StudentJpa> findByGroupIdOrdered(@Param("groupId") Long groupId);

    // Custom query with join fetch to avoid N+1 problem
    @Query("SELECT s FROM StudentJpa s JOIN FETCH s.group WHERE s.id = :id")
    Optional<StudentJpa> findByIdWithGroup(@Param("id") Long id);

    // Count students by group
    @Query("SELECT COUNT(s) FROM StudentJpa s WHERE s.group.id = :groupId")
    Long countByGroupId(@Param("groupId") Long groupId);

    // Update query
    @Modifying
    @Query("UPDATE StudentJpa s SET s.group.id = :newGroupId WHERE s.id = :studentId")
    int updateStudentGroup(@Param("studentId") Long studentId, @Param("newGroupId") Long newGroupId);

    // Delete by userId
    void deleteByUserId(String userId);
}
