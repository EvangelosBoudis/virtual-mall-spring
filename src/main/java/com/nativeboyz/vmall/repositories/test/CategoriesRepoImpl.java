package com.nativeboyz.vmall.repositories.test;

import javax.persistence.*;
import java.util.*;

public class CategoriesRepoImpl implements CategoriesRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, Object> findById(String fields, UUID categoryId) {
        List<String> keys = Arrays.asList(fields.split(","));
        List<Object> values = new ArrayList<>();
        try {
            Object response = entityManager
                    .createQuery("SELECT " + fields + " FROM Category AS c WHERE c.categoryId = :categoryId")
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();
            if (response instanceof Object[]) {
                values.addAll(Arrays.asList((Object[]) response));
            } else if (response != null) {
                values.add(response);
            }
        } catch (NoResultException ignore) { }
        Map<String, Object> dictionary = new HashMap<>();
        if (keys.size() == values.size()) {
            for (int i = 0; i < values.size(); i++) {
                dictionary.put(keys.get(i), values.get(i));
            }
        }
        return dictionary;
    }

}
