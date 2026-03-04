package com.example.thymeleafdemo.repository;

import com.example.thymeleafdemo.entity.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DepartmentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // CREATE
    public Department save(Department department) {
        if (department.getId() == null) {
            entityManager.persist(department);
            return department;
        } else {
            return entityManager.merge(department);
        }
    }

    // READ
    public Optional<Department> findById(Long id) {
        Department department = entityManager.find(Department.class, id);
        return Optional.ofNullable(department);
    }

    public List<Department> findAll() {
        TypedQuery<Department> query = entityManager.createQuery(
            "SELECT d FROM Department d", Department.class);
        return query.getResultList();
    }

    // UPDATE
    public Department update(Department department) {
        return entityManager.merge(department);
    }

    // DELETE
    public void deleteById(Long id) {
        Department department = entityManager.find(Department.class, id);
        if (department != null) {
            entityManager.remove(department);
        }
    }

    public void delete(Department department) {
        entityManager.remove(entityManager.contains(department) ? department : entityManager.merge(department));
    }

    // Custom query: Find by institute ID
    public List<Department> findByInstituteId(Long instituteId) {
        TypedQuery<Department> query = entityManager.createQuery(
            "SELECT d FROM Department d WHERE d.institute.id = :instituteId", Department.class);
        query.setParameter("instituteId", instituteId);
        return query.getResultList();
    }
}
