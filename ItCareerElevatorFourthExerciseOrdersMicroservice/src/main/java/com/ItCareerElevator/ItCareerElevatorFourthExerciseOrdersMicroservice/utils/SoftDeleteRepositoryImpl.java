package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.interfaces.SoftDeletable;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class SoftDeleteRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements SoftDeleteRepository<T, ID> {

    private final EntityManager entityManager;
    private final JpaEntityInformation<T, ?> entityInformation;

    public SoftDeleteRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    @Override
    public List<T> findAll() {
//        return findAll((Specification<T>) null); // ? Claude's version
        return findAll(notDeleted());
    }

    @Override
    public List<T> findAll(Sort sort) {
        return findAll(notDeleted(), sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return findAll(notDeleted(), pageable);
    }

    @Override
    public Optional<T> findById(ID id) {
        return super
                .findById(id)
                .filter(e -> !isDeleted(e));
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return super
                .findAllById(ids)
                .stream()
                .filter(e -> !isDeleted(e))
                .toList();
    }

    @Override
    public long count() {
        return count(notDeleted());
    }

    @Override
    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    @Override
    @Transactional
    public void delete(T entity) {
        setIsDeleted(entity, true);
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        findById(id).ifPresent(this::delete);
    }

    @Override
    public List<T> findAllIncludingDeleted() {
        return super.findAll();
    }

    @Override
    public Optional<T> findByIdIncludingDeleted(ID id) {
        return super.findById(id);
    }

    private Specification<T> notDeleted() {
        return (root, query, cb) -> cb.equal(root.get("isDeleted"), false);
    }

    private boolean isDeleted(T entity) {
        if (entity instanceof SoftDeletable softDeletable) {
            return softDeletable.getIsDeleted();
        }
        return false;
    }

    private void setIsDeleted(T entity, Boolean value) {
        if (entity instanceof SoftDeletable softDeletable) {

            softDeletable.setIsDeleted(value);

        } else {
            throw new IllegalArgumentException(
                    "Entity of type %s does not implement SoftDeletable and cannot be soft-deleted."
                            .formatted(entity.getClass().getSimpleName())
            );
        }
    }
}
