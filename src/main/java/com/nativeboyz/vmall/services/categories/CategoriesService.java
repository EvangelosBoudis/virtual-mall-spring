package com.nativeboyz.vmall.services.categories;

import com.nativeboyz.vmall.models.criteria.CategoryCriteria;
import com.nativeboyz.vmall.models.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CategoriesService {

    CategoryEntity findCategory(UUID id);

    Page<CategoryEntity> findCategories(Pageable pageable);

    CategoryEntity saveCategory(CategoryCriteria criteria, String filename);

    CategoryEntity updateCategory(UUID id, CategoryCriteria criteria, String filename);

    void deleteCategory(UUID id);

    String findCategoryImageName(UUID id);

}
