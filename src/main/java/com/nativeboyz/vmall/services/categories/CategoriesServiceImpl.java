package com.nativeboyz.vmall.services.categories;

import com.nativeboyz.vmall.models.entities.CategoryEntity;
import com.nativeboyz.vmall.repositories.categories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository repository;

    @Autowired
    public CategoriesServiceImpl(CategoriesRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryEntity findCategory(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Page<CategoryEntity> findCategories(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional
    public CategoryEntity saveCategory(CategoryEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteCategory(UUID id) {
        repository.deleteById(id);
    }

}
