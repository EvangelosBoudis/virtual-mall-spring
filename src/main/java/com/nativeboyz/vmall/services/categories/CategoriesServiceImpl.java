package com.nativeboyz.vmall.services.categories;

import com.nativeboyz.vmall.models.criteria.CategoryCriteria;
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

    private final CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesServiceImpl(CategoriesRepository repository) {
        this.categoriesRepository = repository;
    }

    @Override
    public CategoryEntity findCategory(UUID id) {
        return categoriesRepository.findById(id).orElseThrow();
    }

    @Override
    public Page<CategoryEntity> findCategories(Pageable pageable) {
        return categoriesRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public CategoryEntity saveCategory(CategoryCriteria criteria, String filename) {
        CategoryEntity entity = new CategoryEntity(
                criteria.getTitle(),
                criteria.getDescription(),
                filename
        );
        return categoriesRepository.save(entity);
    }

    @Override
    public CategoryEntity updateCategory(UUID id, CategoryCriteria criteria, String filename) {
        CategoryEntity entity = findCategory(id);
        entity.setTitle(criteria.getTitle());
        entity.setDescription(criteria.getDescription());
        entity.setImageName(filename);
        return categoriesRepository.save(entity);
    }

    @Override
    public void deleteCategory(UUID id) {
        categoriesRepository.deleteById(id);
    }

    @Override
    public String findCategoryImageName(UUID id) {
        return categoriesRepository.findImageNameByCategoryId(id);
    }

}
