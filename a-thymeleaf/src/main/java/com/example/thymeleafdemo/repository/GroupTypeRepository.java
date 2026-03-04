package com.example.thymeleafdemo.repository;

import com.example.thymeleafdemo.entity.GroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupTypeRepository extends JpaRepository<GroupType, Long> {

    Optional<GroupType> findByTitle(String title);

    List<GroupType> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT gt FROM GroupType gt LEFT JOIN FETCH gt.groups WHERE gt.id = :id")
    Optional<GroupType> findByIdWithGroups(@Param("id") Long id);
}
