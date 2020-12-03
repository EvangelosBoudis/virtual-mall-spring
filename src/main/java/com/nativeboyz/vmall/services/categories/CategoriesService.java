package com.nativeboyz.vmall.services.categories;

import com.nativeboyz.vmall.models.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CategoriesService {

    CategoryEntity findCategory(UUID id);

    Page<CategoryEntity> findCategories(Pageable pageable);

    CategoryEntity saveCategory(CategoryEntity entity);

    void deleteCategory(UUID id);

}
