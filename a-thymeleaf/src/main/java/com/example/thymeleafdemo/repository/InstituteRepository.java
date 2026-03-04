package com.example.thymeleafdemo.repository;

import com.example.thymeleafdemo.entity.Institute;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class InstituteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // CREATE
    public Institute save(Institute institute) {
        if (institute.getId() == null) {
            entityManager.persist(institute);
            return institute;
        } else {
            return entityManager.merge(institute);
        }
    }

    // READ
    public Optional<Institute> findById(Long id) {
        Institute institute = entityManager.find(Institute.class, id);
        return Optional.ofNullable(institute);
    }

    public List<Institute> findAll() {
        TypedQuery<Institute> query = entityManager.createQuery(
            "SELECT i FROM Institute i", Institute.class);
        return query.getResultList();
    }

    // UPDATE
    public Institute update(Institute institute) {
        return entityManager.merge(institute);
    }

    // DELETE
    public void deleteById(Long id) {
        Institute institute = entityManager.find(Institute.class, id);
        if (institute != null) {
            entityManager.remove(institute);
        }
    }

    public void delete(Institute institute) {
        entityManager.remove(entityManager.contains(institute) ? institute : entityManager.merge(institute));
    }

    // Custom query: Find by name
    public List<Institute> findByNameContaining(String name) {
        TypedQuery<Institute> query = entityManager.createQuery(
            "SELECT i FROM Institute i WHERE i.name LIKE :name", Institute.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
}
