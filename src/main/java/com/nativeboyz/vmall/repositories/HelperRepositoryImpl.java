package com.nativeboyz.vmall.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.StreamSupport;

public class HelperRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> {

    private final JpaEntityInformation entityInformation;
    private final EntityManager entityManager;

    HelperRepositoryImpl(JpaEntityInformation entityInformation,
                         EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.entityInformation = entityInformation;
        // Keep the EntityManager around to used from the newly introduced methods.
        this.entityManager = entityManager;
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        List<T> data = super.findAllById(ids);
        long idsSize = StreamSupport.stream(ids.spliterator(), false).count();
        if (data.size() != idsSize) {
            throw new EmptyResultDataAccessException(String.format("No %s entity with id %s exists!", entityInformation.getJavaType(), ids), (int) idsSize);
        }
        return data;
    }

}
